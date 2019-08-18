package com.acron.demo.core.base.exception;

/**
 * @author Acron
 * @ClassName ParamErrorException
 * @Description TODO
 * @since 2019/08/02 22:04
 */
public class ParamErrorException extends BaseException {
    public ParamErrorException() {
        super();
    }

    public ParamErrorException(String message) {
        super(message);
    }

    public ParamErrorException(Integer code, String message) {
        super(code, message);
    }
}
