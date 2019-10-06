package cn.boosp.sharebook.common.exception;

public class PayNotSuccessException extends Exception {
    public PayNotSuccessException() {
        this("支付不成功");
    }

    public PayNotSuccessException(String message) {
        super(message,null);
    }

    public PayNotSuccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
