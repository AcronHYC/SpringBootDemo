package com.acron.demo.core.base.exception;

/**
 * @author Acron
 * @ClassName NotExistedException
 * @Description TODO
 * @since 2019/08/02 22:03
 */
public class NotExistedException extends BaseException {
    public NotExistedException() {
        super();
    }

    public NotExistedException(String message) {
        super(message);
    }

    public NotExistedException(Integer code, String message) {
        super(code, message);
    }
}
