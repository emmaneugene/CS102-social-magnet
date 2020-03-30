package com.g1t11.socialmagnet.model.social;

public class ThreadNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ThreadNotFoundException() {
        super();
    }

    public ThreadNotFoundException(Throwable e) {
        super(e);
    }
}
