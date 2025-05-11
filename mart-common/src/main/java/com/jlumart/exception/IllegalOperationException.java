package com.jlumart.exception;

public class IllegalOperationException extends BaseException {
    public IllegalOperationException() {
        super("操作异常");
    }
    public IllegalOperationException(String message) {
        super(message);
    }
}
