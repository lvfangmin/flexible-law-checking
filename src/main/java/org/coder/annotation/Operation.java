package org.coder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.coder.Constants;

/**
 * Annotation used to define the metadata of Op.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Operation {

    /**
     * The operation name used in the rules, this value is case insensitive.
     *
     * This field of Op annotation should keep the same as which is used
     * in the rules. @{link OpScanner} will scan all Ops, and maintain a
     * map of Op rule name to Op Class.
     *
     * @{link OpTypeIdResolver} uses this map to do the polymorphic initialize
     * of different concrete class of Op.
     */
    String name();

    /**
     *  The required parameters number of this Op, subclass of {@link AtomicOp}
     *  need to set the this num if you want {@link OpAuditor} to audit the
     *  rule format.
     *
     *  Default value is ANY, which means no limitation on parameters numbers.
     */
    int requiredParamNum() default Constants.ANY;
}
