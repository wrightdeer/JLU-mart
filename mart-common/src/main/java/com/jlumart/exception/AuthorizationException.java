package com.jlumart.exception;

import com.jlumart.constant.MessageConstant;

public class AuthorizationException extends BaseException {
    public AuthorizationException() {
        super(MessageConstant.AUTHORIZATION_ERROR);
    }
    public AuthorizationException(String message) {
        super(message);
    }
}
