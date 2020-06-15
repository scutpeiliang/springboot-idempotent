package com.cris.springbootidempotent.exception;

public class IdempotentException extends Exception{
    public IdempotentException() {
        super();
    }

    public IdempotentException(String msg) {
        super(msg);
    }
}
