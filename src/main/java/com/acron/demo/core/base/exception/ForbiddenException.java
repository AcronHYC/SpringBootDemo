package com.acron.demo.core.base.exception;

import lombok.Data;

/**
 * @author Acron
 * @ClassName ForbiddenException
 * @Description TODO
 * @since 2019/08/02 22:02
 */
public class ForbiddenException extends BaseException {
    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(Integer code, String message) {
        super(code, message);
    }
}
