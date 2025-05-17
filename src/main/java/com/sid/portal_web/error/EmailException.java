package com.sid.portal_web.error;

public class EmailException extends RuntimeException {

  private final String errorMessage;

    public EmailException(String message) {
        this.errorMessage = message;
    }

    public static EmailException blankEmailException() {
      return new EmailException("Email could not be sent");
    }

    public static EmailException notEducationalEmailDomainException() {
      return new EmailException("Email does not belong to an educational domain");
    }

    public static EmailException blankEducationalEmailAddressException() {
      return new EmailException("Institutional email could not be blank");
    }


}
