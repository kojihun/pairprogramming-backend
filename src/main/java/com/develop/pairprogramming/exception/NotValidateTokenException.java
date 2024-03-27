package com.develop.pairprogramming.exception;

public class NotValidateTokenException extends IllegalArgumentException {
    public NotValidateTokenException() {
        super();
    }

    public NotValidateTokenException(String s) {
        super(s);
    }

    public NotValidateTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
