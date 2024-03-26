package com.develop.pairprogramming.controller;

import com.develop.pairprogramming.dto.common.ApiResponse;
import com.develop.pairprogramming.exception.DuplicateEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DuplicateEmailException.class)
    public ApiResponse<?> doWhenDuplicateEmailExceptionThrown(DuplicateEmailException e) {
        return ApiResponse.createError(e.getMessage());
    }
}