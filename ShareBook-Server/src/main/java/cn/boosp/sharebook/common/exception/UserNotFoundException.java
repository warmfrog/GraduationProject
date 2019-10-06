package cn.boosp.sharebook.common.exception;

public class UserNotFoundException extends AccountException {
    public UserNotFoundException() {
        this("用户不存在");
    }

    public UserNotFoundException(String message) {
        super(message,null);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
