package org.coder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to define benchmark attributes.
 * These information will be showed in the usage help.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Benchmark {

    /**
     * The id of this Measurable class.
     */
    String id();

    /**
     * Given detailed description of Measurable class.
     */
    String description();

    /**
     * Describe the args format.
     */
    String args();
}
