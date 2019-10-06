package cn.boosp.sharebook.common.exception;

public class UbookNotFoundException extends BookException {
    public UbookNotFoundException() {
        this("未找到该书");
    }

    public UbookNotFoundException(String message) {
        super(message, null);
    }

    public UbookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
