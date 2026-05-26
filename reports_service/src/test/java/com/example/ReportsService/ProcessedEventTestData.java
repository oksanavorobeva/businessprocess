package com.example.ReportsService;


import com.example.ReportsService.persistence.model.ProcessedEventEntity;

public class ProcessedEventTestData {

    public static String messageId = "testMessage";

    public static ProcessedEventEntity createProcessedEventEntity() {
        Long id = 123L;
        String messageId = "testMessage";
        Long reportEventId = 1L;
        String reportName = "testReport";
        return new ProcessedEventEntity(
                id,
                messageId,
                reportEventId,
                reportName);
    }
}
