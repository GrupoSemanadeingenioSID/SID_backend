package com.sid.portal_web.error;

import lombok.Getter;

@Getter
public class RegisterRequestException extends RuntimeException {

    private final String messageError;

    private RegisterRequestException(String message) {
        super(message);
        this.messageError = message;
    }

    public static RegisterRequestException credentialsError(String message) {
        return new RegisterRequestException("Register credentials error: "+message);
    }

}
