package com.sid.portal_web.error;


import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @lombok.Data
    @lombok.Builder
    private static class ErrorResponse {
        private String message;
        private int status;
    }
}
