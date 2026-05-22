package com.example.sales_metrics_worker.log;


import by.javaguru.core.SalesReportEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UtilLogging {

    public void sendSalesReportEvent
            (SalesReportEvent salesReport) {
        log.info("Send simple sales report for: {}",
                salesReport.getOrderId()
                + " " + salesReport.getReportName()
                + " " + salesReport.getSalesReportEvents()
                + " " + salesReport.getReportDate()
                + " " + salesReport.getReportName()
                + " " + salesReport.getUserEmail()
                + " " + salesReport.getReportDate());
    }
}
