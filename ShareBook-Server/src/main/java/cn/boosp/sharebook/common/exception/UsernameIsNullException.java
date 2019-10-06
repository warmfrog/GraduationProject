package cn.boosp.sharebook.common.exception;

public class UsernameIsNullException extends AccountException {
    public UsernameIsNullException() {
        this("用户名不能为空");
    }

    public UsernameIsNullException(String message) {
        super(message,null);
    }

    public UsernameIsNullException(String message, Throwable cause) {
        super(message, cause);
    }
}
