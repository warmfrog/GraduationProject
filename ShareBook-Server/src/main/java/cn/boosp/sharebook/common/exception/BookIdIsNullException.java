package cn.boosp.sharebook.common.exception;

public class BookIdIsNullException extends BookException {
    public BookIdIsNullException() {
        super("图书ID不能为空");
    }
}
