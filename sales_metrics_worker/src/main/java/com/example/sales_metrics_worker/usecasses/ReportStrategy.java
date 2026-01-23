package com.example.sales_metrics_worker.usecasses;

import by.javaguru.core.ReportCreatedEvent;

public interface ReportStrategy {
    void generateReport(ReportCreatedEvent reportCreatedEvent);
}