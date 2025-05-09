package com.jlumart.exception;

import com.jlumart.constant.MessageConstant;

public class ForbiddenOperationException extends BaseException {
    public ForbiddenOperationException() {
        super(MessageConstant.FORBIDDEN_OPERATION);
    }
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
