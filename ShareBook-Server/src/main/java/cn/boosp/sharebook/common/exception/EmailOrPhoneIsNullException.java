package cn.boosp.sharebook.common.exception;

public class EmailOrPhoneIsNullException extends AccountException {
    public EmailOrPhoneIsNullException() {
        this("邮箱,手机不能同时为空");
    }

    public EmailOrPhoneIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailOrPhoneIsNullException(String s) {
        super(s,null);
    }
}
