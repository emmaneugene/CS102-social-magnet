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
        NO_CONNECTION(0),
        DUPLICATE_KEY(1062),
        THREAD_NOT_FOUND(23526),
        USER_NOT_FOUND(23527),
        REQUEST_EXISTING_FRIEND(23528),
        REQUEST_SELF(23529),
        REGISTER_EXISTING_USER(23530),
        PLANT_ON_EXISTING_CROP(23531),
        NOT_ENOUGH_INVENTORY(23532),
        NOT_ENOUGH_WEALTH(23533),
        PLOT_INACCESSIBLE(23534),
        TAG_NON_FRIEND(23535);

        public int value;

        private SQLErrorCode(int code) {
            this.value = code;
        }
    }
    private static final long serialVersionUID = 1L;

    private SQLErrorCode code = null;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(SQLErrorCode code) {
        this.code = code;
    }

    public DatabaseException(Throwable e) {
        super(e);
    }

    public SQLErrorCode getCode() {
        return code;
    }
}
