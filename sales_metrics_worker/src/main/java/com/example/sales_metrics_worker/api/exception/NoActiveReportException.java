package com.example.sales_metrics_worker.api.exception;

public class NoActiveReportException extends RuntimeException {

    public NoActiveReportException(String message) {
        super(message);
    }
}
