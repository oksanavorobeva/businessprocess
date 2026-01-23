package by.javaguru.core.exception;

import java.time.ZonedDateTime;

public record ErrorResponse(
        int statusCode,
        String message,
        ZonedDateTime timeStamp
) {
}

