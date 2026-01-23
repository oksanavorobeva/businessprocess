package by.javaguru.core.exception;

public class NonRetryableException extends RuntimeException {

    public NonRetryableException(Throwable cause) {
        super(cause);
    }
}
