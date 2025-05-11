package com.jlumart.exception;

import com.jlumart.constant.MessageConstant;

public class ForbiddenOperationException extends BaseException {
    public ForbiddenOperationException() {
        super("权限异常");
    }
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
