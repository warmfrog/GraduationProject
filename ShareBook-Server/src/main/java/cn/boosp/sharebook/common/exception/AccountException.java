package cn.boosp.sharebook.common.exception;

public class AccountException extends Exception {
    public AccountException() {
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountException(String message) {
        super(message);
    }
}
