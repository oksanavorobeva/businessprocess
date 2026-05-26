package com.example.ReportsService;


import by.javaguru.core.ReportCreatedEvent;


import java.time.LocalDate;


public class ReportCreatedEventData {

    public static ReportCreatedEvent createdReportCreatedEvent() {
        Long orderId = 123L;
        String reportName = "test";
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        String userEmail = "test@example.com";
        return new ReportCreatedEvent(
                orderId,
                reportName,
                rangeStart,
                rangeEnd,
                userEmail
        );
    }
}
