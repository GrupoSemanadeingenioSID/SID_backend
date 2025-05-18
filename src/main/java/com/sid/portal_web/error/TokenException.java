package com.sid.portal_web.error;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final String errorMessage;

    public TokenException(final String message) {
        super(message);
        this.errorMessage = message;
    }


    public static TokenException emptyTokenException() {
        return new TokenException("Empty token");
    }

    public static TokenException tokenNotFoundException() {
        return new TokenException("Token Not Found");
    }

    public static TokenException invalidTokenException() {
        return new TokenException("Invalid Token");
    }

    public static TokenException expiredTokenException() {
        return new TokenException("Expired Token");
    }

}
