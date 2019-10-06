package cn.boosp.sharebook.common.exception;

public class UserAlreadyExistException extends AccountException {
    public UserAlreadyExistException() {
        this("用户已存在");
    }

    public UserAlreadyExistException(String message) {
        super(message,null);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
