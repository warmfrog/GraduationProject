package cn.boosp.sharebook.common.exception;

public class MoneyException extends Exception {
    public MoneyException() {
    }

    public MoneyException(String message) {
        super(message);
    }

    public MoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}
