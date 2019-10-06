package cn.boosp.sharebook.common.exception;

public class BookNotFoundException extends BookException {
    public BookNotFoundException() {
        this("该书不存在");
    }

    public BookNotFoundException(String message) {
        super(message,null);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
