package com.example.sales_metrics_worker.usecasses;

import by.javaguru.core.CalculatedSalesReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import by.javaguru.core.SalesReportEvent;
import by.javaguru.core.exception.OrderDataMissingException;
import com.example.sales_metrics_worker.kafka.ReportCreatedEventsProducer;
import com.example.sales_metrics_worker.persistence.model.SalesReportData;
import com.example.sales_metrics_worker.persistence.repository.SalesReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component("simpleSalesReportStrategy")
@RequiredArgsConstructor
@Slf4j
public class SimpleSalesReportStrategy implements ReportStrategy {

    private final SalesReportRepository salesReportRepository;
    private final ReportCreatedEventsProducer reportCreatedEventsProducer;

    public void generateReport(ReportCreatedEvent reportCreatedEvent) {
        log.info("Generating simple sales report for: {}", reportCreatedEvent.getReportName());

        List<SalesReportData> salesData = salesReportRepository
                .findSalesDataBetweenDates(reportCreatedEvent.getRangeStart(), reportCreatedEvent.getRangeEnd());

        List<CalculatedSalesReportEvent> calculatedReports = calculateSalesReport(salesData);

        var salesReport = mapperSalesReportEvent(reportCreatedEvent, calculatedReports);

        try {
            reportCreatedEventsProducer.sendOrderEvent(salesReport);
        } catch (Exception e) {
            throw new OrderDataMissingException(e.getMessage());
        }

        log.info("Send simple sales report for: {}",
                salesReport.getOrderId()
                + " " + salesReport.getReportName()
                + " " + salesReport.getSalesReportEvents()
                + " " + salesReport.getReportDate()
                + " " + salesReport.getReportName()
                + " " + salesReport.getUserEmail()
                + " " + salesReport.getReportDate());

        calculatedReports.forEach(report -> log.info("Product: {}, Total Quantity: {}, Total Price: {}",
                report.getProductName(), report.getTotalQuantitySold(), report.getTotalPrice()));
    }

    private SalesReportEvent mapperSalesReportEvent(ReportCreatedEvent reportCreatedEvent, List<CalculatedSalesReportEvent> calculatedReports) {
        SalesReportEvent salesReport = new SalesReportEvent();
        salesReport.setOrderId(reportCreatedEvent.getOrderId());
        salesReport.setReportName(reportCreatedEvent.getReportName());
        salesReport.setRangeEnd(reportCreatedEvent.getRangeEnd());
        salesReport.setRangeStart(reportCreatedEvent.getRangeStart());
        salesReport.setUserEmail(reportCreatedEvent.getUserEmail());
        salesReport.setSalesReportEvents(calculatedReports);
        salesReport.setReportDate(LocalDateTime.now());
        return salesReport;
    }

    private List<CalculatedSalesReportEvent> calculateSalesReport(List<SalesReportData> salesData) {
        Map<String, List<SalesReportData>> groupedSales = salesData.stream()
                .collect(Collectors.groupingBy(SalesReportData::getProductName));

        return groupedSales.entrySet().stream()
                .map(entry -> {
                    String productName = entry.getKey();
                    List<SalesReportData> productSales = entry.getValue();
                    int totalQuantitySold = productSales.stream()
                            .mapToInt(SalesReportData::getQuantitySold)
                            .sum();
                    BigDecimal totalPrice = productSales.stream()
                            .map(sale -> sale.getPricePerUnit().multiply(BigDecimal.valueOf(sale.getQuantitySold())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(2, RoundingMode.HALF_UP);
                    return new CalculatedSalesReportEvent(productName, totalQuantitySold, totalPrice);
                })
                .collect(Collectors.toList());
    }
}
