package cn.boosp.sharebook.common.exception;

public class BookException extends Exception {
    public BookException() {
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
}
