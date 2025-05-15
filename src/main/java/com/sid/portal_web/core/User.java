package com.sid.portal_web.core;

import com.sid.portal_web.error.UserException;

public record User(Integer id, String email, String password) {

    public User {
        if (email == null || email.isBlank()) {
            throw UserException.emailException();
        }
        if (password == null || password.length() < 8) {
            throw UserException.passwordLengthException();
        }
    }

    public String emailDomain() {
        return email.substring(email.indexOf("@") + 1);
    }

}
