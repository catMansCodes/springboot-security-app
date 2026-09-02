package com.catmanscodes.securityapp.exception;

public class UserNotFoundedException extends RuntimeException {

    public UserNotFoundedException(String message) {
        super(message);
    }
}
