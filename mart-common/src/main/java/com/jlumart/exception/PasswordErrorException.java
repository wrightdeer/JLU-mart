package com.jlumart.exception;

import com.jlumart.constant.MessageConstant;

public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {
        super(MessageConstant.PASSWORD_ERROR);
    }
    public PasswordErrorException(String message) {
        super(message);
    }
}
