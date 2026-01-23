package by.javaguru.core.exception;

public class RetryableException extends RuntimeException {

    public RetryableException(Throwable cause) {
        super(cause);
    }
}
