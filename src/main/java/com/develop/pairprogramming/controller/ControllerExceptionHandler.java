package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.exception.DuplicateEmailException;
import com.develop.pairprogramming.exception.NotFoundMemberException;
import com.develop.pairprogramming.exception.NotValidateTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ApiResponse<?> doWhenDuplicateEmailExceptionThrown(DuplicateEmailException e) {
        return ApiResponse.createError(e.getMessage());
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ApiResponse<?> doWhenNotFoundMemberExceptionThrown(NotFoundMemberException e) {
        return ApiResponse.createError(e.getMessage());
    }

    @ExceptionHandler(NotValidateTokenException.class)
    public ApiResponse<?> doWhenNotValidateTokenExceptionThrown(NotValidateTokenException e) {
        return ApiResponse.createError(e.getMessage());
    }
}