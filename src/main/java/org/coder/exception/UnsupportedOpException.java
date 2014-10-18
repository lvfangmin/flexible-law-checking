package org.coder.exception;

/**
 * If the client caught this exception, then you should check the
 * filter json rules, you may use one of the unsupported operation.
 */
public class UnsupportedOpException extends RuntimeException {

    public UnsupportedOpException() {
        super();
    }

    public UnsupportedOpException(String message) {
        super(message);
    }

    public UnsupportedOpException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedOpException(Throwable cause) {
        super(cause);
    }

    // random generated serial version id.
    private static final long serialVersionUID = -7906256775080422185L;
}
