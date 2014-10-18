package org.coder.exception;

/**
 * Will be thrown in @{link OpAuditor}, when the given rules Op parameters
 * number doesn't fit the one defined in the @{link Op}'s annotation.
 */
public class IllegalParamNumException extends RuntimeException {

    public IllegalParamNumException() {
        super();
    }

    public IllegalParamNumException(String message) {
        super(message);
    }

    public IllegalParamNumException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParamNumException(Throwable cause) {
        super(cause);
    }

    // random generated serial version id.
    private static final long serialVersionUID = 2502544997171430559L;
}
