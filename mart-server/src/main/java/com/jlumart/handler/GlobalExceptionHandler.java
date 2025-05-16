package com.jlumart.handler;

import com.jlumart.constant.MessageConstant;
import com.jlumart.exception.BaseException;
import com.jlumart.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        log.error("数据库唯一约束冲突异常：", ex);

        // 判断是否是 username 字段的唯一性冲突
        if (ex.getMessage().contains("JLUMARTADMIN.USERNAME_UK") || ex.getMessage().contains("JLUMARTADMIN.USER_USERNAME")) {
            return Result.error("用户名已存在");
        }
        // 判断是否违反库存的非负限性约束
        if (ex.getMessage().contains("JLUMARTADMIN.chk_stock_non_negative")) {
            return Result.error("库存不能小于0");
        }

        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

}
