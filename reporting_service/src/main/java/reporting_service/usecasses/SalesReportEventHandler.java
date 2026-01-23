package reporting_service.usecasses;

import by.javaguru.core.CalculatedSalesReportEvent;
import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.SalesReportEvent;
import by.javaguru.core.exception.OrderDataMissingException;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import reporting_service.kafka.ReportEmailEventsProducer;
import reporting_service.kafka.ReportReadylEventsProducer;

@Component
@RequiredArgsConstructor
@Slf4j
public class SalesReportEventHandler implements DtoReportHandler<SalesReportEvent> {

    private final ReportGenerationStrategy<CalculatedSalesReportEvent, SalesReportEvent> reportGenerationStrategy;
    private final ReportEmailEventsProducer reportEmailEventsProducer;
    private final ReportReadylEventsProducer reportReadylEventsProducer;

    @Value("${report.source.file}")
    private String reportSourceFile;

    @Value("${report.destination.directory}")
    private String reportDestinationDirectory;

    @Override
    public void handleDtoAndGenerateReport(SalesReportEvent salesReportEvent) {
        try {
            String destFileName = generateDestinationFileName(salesReportEvent.getOrderId());
            generateSalesReport(salesReportEvent.getSalesReportEvents(), destFileName, salesReportEvent);
            log.info("SalesReport generated successfully for message ID: {}");

            ReadyReportEvent readySalesReportEvent = new ReadyReportEvent();
            readySalesReportEvent.setOrderId(salesReportEvent.getOrderId());
            readySalesReportEvent.setReportName(salesReportEvent.getReportName());
            readySalesReportEvent.setRangeStart(salesReportEvent.getRangeStart());
            readySalesReportEvent.setRangeEnd(salesReportEvent.getRangeEnd());
            readySalesReportEvent.setUserEmail(salesReportEvent.getUserEmail());
            readySalesReportEvent.setFilePath(destFileName);

            try {
                reportEmailEventsProducer.sendOrderEvent(readySalesReportEvent);
                reportReadylEventsProducer.sendOrderEvent(readySalesReportEvent);
            } catch (Exception e) {
                throw new OrderDataMissingException(e.getMessage());
            }

            log.info("Send simple sales report for: {}",
                    readySalesReportEvent.getOrderId()
                    + " " + readySalesReportEvent.getReportName()
                    + " " + readySalesReportEvent.getRangeStart()
                    + " " + readySalesReportEvent.getRangeEnd()
                    + " " + readySalesReportEvent.getUserEmail()
                    + " " + readySalesReportEvent.getFilePath());

        } catch (Exception e) {
            log.error("Error generating SalesReport for message ID: {}", e);
            throw new RuntimeException("Failed to generate SalesReport", e);
        }
    }

    private String generateDestinationFileName(Long reportId) {
        String fileName = "sales_report_" + reportId + ".pdf";
        return reportDestinationDirectory + File.separator + fileName;
    }

    private void generateSalesReport(List<CalculatedSalesReportEvent> data, String destFileName,
                                     SalesReportEvent salesReportEvent) throws Exception {
        try {
            reportGenerationStrategy.generateReport(reportSourceFile, destFileName, salesReportEvent, data);
        } catch (JRException e) {
            log.error("Error during SalesReport compilation and generation: {}", e.getMessage(), e);
            throw e;
        }
    }
}