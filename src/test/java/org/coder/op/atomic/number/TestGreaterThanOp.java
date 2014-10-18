package org.coder.op.atomic.number;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestGreaterThanOp {

    @Test
    public void testGreaterThanOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "9");

        // 1. test accepted attribute
        GreaterThanOp op = new GreaterThanOp();
        op.add("number");
        op.add("8");
        Assert.assertTrue(op.apply(attributes));

        // 2. test non-accepted attribute
        op.set(1, "10");
        Assert.assertFalse(op.apply(attributes));

        // 3. test attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));
    }

    @Test
    public void testGreaterThanOpWithInvalidNumber() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "1.2abe");

        GreaterThanOp op = new GreaterThanOp();
        op.add("number");
        op.add("1");
        Assert.assertFalse(op.apply(attributes));
    }
}
