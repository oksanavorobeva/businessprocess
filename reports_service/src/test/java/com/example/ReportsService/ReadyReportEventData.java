package com.example.ReportsService;


import by.javaguru.core.ReadyReportEvent;

import java.time.LocalDate;


public class ReadyReportEventData {

    public static ReadyReportEvent createdReadyReportEvent() {
        Long orderId = 123L;
        String reportNam = "test";
        LocalDate rangeStart = LocalDate.of(2023, 1, 1);
        LocalDate rangeEnd = LocalDate.of(2023, 1, 31);
        String userEmail = "test@example.com";
        String filePat = "test.pdf";
        return new ReadyReportEvent(
                orderId,
                reportNam,
                rangeStart,
                rangeEnd,
                userEmail,
                filePat
        );
    }
}
