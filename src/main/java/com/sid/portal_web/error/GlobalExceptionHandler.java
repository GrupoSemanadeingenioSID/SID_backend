package com.sid.portal_web.error;


import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {





    @lombok.Data
    @lombok.Builder
    static class ErrorResponse {
        private String message;
        private int status;
    }
}
