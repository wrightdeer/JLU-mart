package com.jlumart.exception;

import com.jlumart.constant.MessageConstant;

/**
 * 账号不存在异常
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
        super(MessageConstant.ACCOUNT_NOT_FOUND);
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}
