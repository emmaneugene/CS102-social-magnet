package com.g1t11.socialmagnet.model.social;

public class RegisterExistingUserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegisterExistingUserException() {
        super();
    }

    public RegisterExistingUserException(Throwable e) {
        super(e);
    }
}
