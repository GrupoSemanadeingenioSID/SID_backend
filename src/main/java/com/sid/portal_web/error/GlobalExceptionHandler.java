package com.sid.portal_web.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // apartado para usuarios:
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    // LoginRequestException
    @ExceptionHandler(LoginRequestException.class)
    public ResponseEntity<ErrorResponse> handleLoginRequestException(LoginRequestException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    // RegisterRequestException
    @ExceptionHandler(RegisterRequestException.class)
    public ResponseEntity<ErrorResponse> hanlderRegisterRequestException(RegisterRequestException ex) {
        ErrorResponse err = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value()) // 400
                .build();

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }


    @lombok.Data
    @lombok.Builder
    private static class ErrorResponse {
        private String message;
        private int status;
    }
}
