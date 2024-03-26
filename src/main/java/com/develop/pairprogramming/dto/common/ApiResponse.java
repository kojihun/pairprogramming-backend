package com.develop.pairprogramming.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String FAIL_STATUS = "FAIL";
    private static final String ERROR_STATUS = "ERROR";

    private ApiResponse() {
    }

    @Builder
    private ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> createSuccess(T data) {
        return ApiResponse.<T>builder()
                .status(SUCCESS_STATUS)
                .data(data)
                .build();
    }

    public static ApiResponse<?> createSuccessWithNoContent() {
        return ApiResponse.builder()
                .status(SUCCESS_STATUS)
                .build();
    }

    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }

        return ApiResponse.builder()
                .status(FAIL_STATUS)
                .data(errors)
                .build();
    }

    public static ApiResponse<?> createError(String message) {
        return ApiResponse.builder()
                .status(ERROR_STATUS)
                .message(message)
                .build();
    }
}