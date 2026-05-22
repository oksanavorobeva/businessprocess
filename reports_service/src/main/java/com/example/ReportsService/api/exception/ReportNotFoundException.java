package com.example.ReportsService.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
