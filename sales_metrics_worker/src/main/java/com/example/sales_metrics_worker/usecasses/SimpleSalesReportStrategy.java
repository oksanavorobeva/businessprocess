package com.example.sales_metrics_worker.usecasses;

import by.javaguru.core.CalculatedSalesReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import by.javaguru.core.SalesReportEvent;
import com.example.sales_metrics_worker.persistence.model.SalesReportData;
import com.example.sales_metrics_worker.persistence.repository.SalesReportRepository;
import com.example.sales_metrics_worker.usecasses.mapper.SalesReportEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component("simpleSalesReportStrategy")
@RequiredArgsConstructor
@Slf4j
public class SimpleSalesReportStrategy implements ReportStrategy {

    private final SalesReportRepository salesReportRepository;
    private final SalesReportEventMapper salesReportEventMapper;
    private final OutboxService outboxService;

    @Override
    public String getStrategyName() {
        return "simpleSalesReportStrategy";
    }

    public void generateReport(ReportCreatedEvent reportCreatedEvent) {
        log.info("Generating simple sales report for: {}", reportCreatedEvent.getReportName());

        List<CalculatedSalesReportEvent> calculatedReports = calculateSalesReport(reportCreatedEvent);

        SalesReportEvent salesReport = salesReportEventMapper.toSalesReportEvent(reportCreatedEvent, calculatedReports);

        outboxService.createSalesReportCreatedEvent(salesReport);
    }

    private List<CalculatedSalesReportEvent> calculateSalesReport(ReportCreatedEvent reportCreatedEvent) {
        List<SalesReportData> salesData = salesReportRepository
                .findSalesDataBetweenDates(reportCreatedEvent.getRangeStart(), reportCreatedEvent.getRangeEnd());

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
