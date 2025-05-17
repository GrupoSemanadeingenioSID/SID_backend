package com.sid.portal_web.core;

import com.sid.portal_web.error.EmailException;
import com.sid.portal_web.error.UserException;

import java.util.Objects;

public record User(
        Integer id,
        String email,
        String password,
        String institutionalEmail,
        boolean active) {

    public User {
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        Objects.requireNonNull(institutionalEmail, "Institutional email cannot be null");

        if (email.isBlank()) {
            throw EmailException.blankEmailException();
        }
        if (password.length() < 8) {
            throw UserException.passwordLengthException();
        }
        if (!isEducationalEmailDomain(institutionalEmail)) {
            throw EmailException.notEducationalEmailDomainException();
        }
    }
    public boolean isEducationalEmailDomain(String email) {
        if (email == null) {
            return false;
        }
        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            return false;
        }
        String domain = email.substring(atIndex + 1);
        return domain.endsWith(".edu.co");  // Verifica que el dominio sea educativo
    }

    public boolean isEducationalEmailDomain() {
        return isEducationalEmailDomain(this.institutionalEmail);
    }
}
