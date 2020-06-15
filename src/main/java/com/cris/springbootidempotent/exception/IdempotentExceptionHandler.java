package com.cris.springbootidempotent.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理异常,给用户返回提示信息
 */
@ControllerAdvice
@RestController
public class IdempotentExceptionHandler {
    @ExceptionHandler (IdempotentException.class)
    public String handle(IdempotentException e) {
        return e.getMessage();
    }
}
