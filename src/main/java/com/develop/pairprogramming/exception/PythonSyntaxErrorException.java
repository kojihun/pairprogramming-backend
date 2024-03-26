package com.develop.pairprogramming.exception;

public class PythonSyntaxErrorException extends RuntimeException {
    public PythonSyntaxErrorException() {
        super();
    }

    public PythonSyntaxErrorException(String message) {
        super(message);
    }

    public PythonSyntaxErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
