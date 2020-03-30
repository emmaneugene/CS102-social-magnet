package com.g1t11.socialmagnet.data;

/**
 * An unchecked exception that wraps around SQLException and other database-
 * related exceptions.
 * <p>
 * This allows us to bubble SQLExceptions up our call stack without polluting
 * the codebase with <code>throws</code>.
 */
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
