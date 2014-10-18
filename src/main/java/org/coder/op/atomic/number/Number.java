package org.coder.op.atomic.number;

public class Number implements Comparable<Number> {
    private final String number;

    public Number(String number) {
        this.number = number;
    }

    /**
     * return long value
     *
     * @return
     * @throw NumberFormatException if the number is null or is not a parsable long.
     */
    public Long getValue() {
        return Long.parseLong(number);
    }

    @Override
    public int compareTo(Number other) {
        return getValue().compareTo(other.getValue());
    }
}
