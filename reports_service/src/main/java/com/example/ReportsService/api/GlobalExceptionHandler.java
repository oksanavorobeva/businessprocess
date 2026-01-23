package com.example.ReportsService.api;


import by.javaguru.core.exception.ErrorResponse;
import com.example.ReportsService.api.exception.ReportNotFoundException;
import com.example.ReportsService.api.exception.TopicNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EUROPE_MINSK = "Europe/Minsk";

    @ExceptionHandler(TopicNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleTopicNotFoundException(TopicNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }

    @ExceptionHandler(ReportNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse handleReportNotFoundException(ReportNotFoundException exception) {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                exception.getMessage(),
                ZonedDateTime.now().withZoneSameInstant(ZoneId.of(EUROPE_MINSK)));
    }
}
