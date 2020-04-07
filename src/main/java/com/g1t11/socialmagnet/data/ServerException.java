package com.g1t11.socialmagnet.data;

/**
 * An unchecked exception that handles all errors thrown by the web service.
 * <p>
 * This allows us to bubble SQLExceptions up our call stack without polluting
 * the codebase with <code>throws</code>.
 */
public class ServerException extends RuntimeException {
    /**
     * A list of potential causes of exceptions being thrown by the server.
     */
    public enum ErrorCode {
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

        private ErrorCode(int code) {
            this.value = code;
        }
    }
    private static final long serialVersionUID = 1L;

    /**
     * The ServerError code value.
     */
    private Integer code = null;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(ErrorCode code) {
        this.code = code.value;
    }

    public ServerException(Integer code) {
        this.code = code;
    }

    public ServerException(Throwable e) {
        super(e);
    }

    public Integer getCode() {
        return code;
    }
}
