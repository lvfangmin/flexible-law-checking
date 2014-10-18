package org.coder.op.atomic.number;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestEqualOp {

    @Test
    public void testEqualOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "1");

        // 1. test same attribute
        EqualOp op = new EqualOp();
        op.add("number");
        op.add("1");
        Assert.assertTrue(op.apply(attributes));

        // 2. test different attribute
        op.set(1, "2");
        Assert.assertFalse(op.apply(attributes));

        // 3. test attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));

        // 4. test double number, expect not equal
        attributes.put("number", "6.818");
        Assert.assertFalse(op.apply(attributes));
        op.set(1, "6");
        Assert.assertFalse(op.apply(attributes));
    }

    @Test
    public void testEqualOpWithInvalidVersion() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "1.2abe");

        EqualOp op = new EqualOp();
        op.add("number");
        op.add("1.2abe");
        Assert.assertFalse(op.apply(attributes));
    }
}
