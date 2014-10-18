package org.coder.exception;

/**
 * Will be thrown in @{link Filter.init} if the rule could not mapper
 * to the given Op class.
 */
public class JsonParseException extends Exception {

    public JsonParseException() {
        super();
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }

    // random generated serial version id.
    private static final long serialVersionUID = 6732609788407564249L;
}
