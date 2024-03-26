package com.develop.pairprogramming.exception;

public class DuplicateEmailException extends IllegalStateException{
    public DuplicateEmailException() {
        super();
    }

    public DuplicateEmailException(String s) {
        super(s);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
