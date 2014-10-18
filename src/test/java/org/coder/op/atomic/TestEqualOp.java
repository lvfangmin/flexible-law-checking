package org.coder.op.atomic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.coder.exception.IllegalParamNumException;

public class TestEqualOp {

    /**
     * Aspect functional test, only need to test in one place
     */
    @Test
    public void testAspectFunction() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("lang", "en");

        EqualOp op = new EqualOp();
        op.add("lang");

        // 1. test not enough parameters
        try {
            op.apply(attributes);
            Assert.fail("should throw exception while there is not enough parameters");
        } catch (IllegalParamNumException e) {
        }

        // 2. test trim attribute
        op.add(" en ");
        Assert.assertTrue(op.apply(attributes));

        // 3. test null attributes
        try {
            op.apply(null);
            Assert.fail("should throw exception when passing null attributes");
        } catch (IllegalArgumentException e) {
        }

    }

    @Test
    public void testEqualOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("lang", "en");

        // 1. test same attribute
        EqualOp op = new EqualOp();
        op.add("lang");
        op.add("en");
        Assert.assertTrue(op.apply(attributes));

        // 2. test different attribute
        op.set(1, "cn");
        Assert.assertFalse(op.apply(attributes));

        // 3. test 'lang' attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));
    }

}
