package com.sid.portal_web.error;

import lombok.Getter;

@Getter
public class LoginRequestException extends RuntimeException {

    private final String errorMessage;

    private LoginRequestException(final String message) {
        super(message);
        this.errorMessage = message;
    }


    public static LoginRequestException credentialsException() {
        return new LoginRequestException("Credentials error, email or password null");
    }




}
