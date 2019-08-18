package com.acron.demo.core.base.exception;

/**
 * @author Acron
 * @ClassName UnauthorizedException
 * @Description TODO
 * @since 2019/08/02 22:04
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Integer code, String message) {
        super(code, message);
    }
}
