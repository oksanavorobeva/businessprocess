package com.example.ReportsService.log;

import by.javaguru.core.ReadyReportEvent;
import by.javaguru.core.ReportCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UtilLogging {
    public void sendOrderEvent(ReportCreatedEvent reportCreatedEvent) {
        log.info("send order event" + " " + reportCreatedEvent.getOrderId()
                 + " " + reportCreatedEvent.getReportName()
                 + " " + reportCreatedEvent.getRangeStart()
                 + " " + reportCreatedEvent.getRangeEnd()
                 + " " + reportCreatedEvent.getUserEmail());
    }

    public void sendCacheEvent(ReadyReportEvent readyReportEvent) {
        log.info("send cache event" + " " + readyReportEvent.getOrderId()
                 + " " + readyReportEvent.getReportName()
                 + " " + readyReportEvent.getRangeStart()
                 + " " + readyReportEvent.getRangeEnd()
                 + " " + readyReportEvent.getUserEmail()
                 + " " + readyReportEvent.getFilePath());
    }
}
