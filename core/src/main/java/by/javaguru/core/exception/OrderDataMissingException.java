package by.javaguru.core.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDataMissingException extends RuntimeException {

    public OrderDataMissingException(String message) {
        super(message);
        log.error(message);
    }
}
