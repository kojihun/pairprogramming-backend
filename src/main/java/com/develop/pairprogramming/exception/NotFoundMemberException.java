package com.develop.pairprogramming.exception;

public class NotFoundMemberException extends IllegalStateException{
    public NotFoundMemberException() {
        super();
    }

    public NotFoundMemberException(String s) {
        super(s);
    }

    public NotFoundMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
