package com.sid.portal_web.error;

public class UserException extends RuntimeException {

    private final String messageError;

    public UserException(String messageError) {
        this.messageError = messageError;
    }


    public static UserException emailException()
    {
        return new UserException("Email must not be blank");
    }

    public static UserException passwordLengthException()
    {
        return new UserException("Password must have at least 8 characters");
    }

    public static UserException userNotFound()
    {
        return new UserException("User not found");
    }

    public static UserException userAlreadyExists()
    {
        return new UserException("User already exists");
    }


}
