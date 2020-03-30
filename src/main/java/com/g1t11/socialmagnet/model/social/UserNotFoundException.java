package com.g1t11.socialmagnet.model.social;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Throwable e) {
        super(e);
    }
}
