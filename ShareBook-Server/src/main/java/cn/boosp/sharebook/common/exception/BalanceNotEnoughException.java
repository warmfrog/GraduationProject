package cn.boosp.sharebook.common.exception;

public class BalanceNotEnoughException extends PayNotSuccessException {
    public BalanceNotEnoughException() {
        this("余额不足，支付失败");
    }

    public BalanceNotEnoughException(String message) {
        super(message);
    }

    public BalanceNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }
}
