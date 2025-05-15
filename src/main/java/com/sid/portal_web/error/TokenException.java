package com.sid.portal_web.error;

import lombok.Getter;

@Getter
public class TokenException extends RuntimeException {

    private final String errorMessage;

    public TokenException(final String message) {

        this.errorMessage = message;
    }


    public static TokenException emptyTokenException() {
        return new TokenException("Empty token");
    }

}
