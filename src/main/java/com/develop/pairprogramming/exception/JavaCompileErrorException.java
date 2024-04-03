package com.develop.pairprogramming.exception;

public class JavaCompileErrorException extends RuntimeException {
    public JavaCompileErrorException() {
        super();
    }

    public JavaCompileErrorException(String message) {
        super(message);
    }

    public JavaCompileErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
