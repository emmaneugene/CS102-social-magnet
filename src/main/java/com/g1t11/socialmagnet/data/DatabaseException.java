package com.g1t11.socialmagnet.data;

public class DatabaseException extends RuntimeException {
    public enum SQLErrorCode {
        NO_CONNECTION(0);

        public int code;

        private SQLErrorCode(int code) {
            this.code = code;
        }
    }

    private static final long serialVersionUID = 1L;
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable e) {
        super(e);
    }
}