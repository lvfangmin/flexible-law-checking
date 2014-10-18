package org.coder.op.atomic.number;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestLessThanOp {

    @Test
    public void testVersionLessThanOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "8");

        // 1. test accepted attribute
        LessThanOp op = new LessThanOp();
        op.add("number");
        op.add("9");
        Assert.assertTrue(op.apply(attributes));

        // 2. test non-accepted attribute
        op.set(1, "7");
        Assert.assertFalse(op.apply(attributes));

        // 3. test attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));
    }

    @Test
    public void testLessThanOpWithInvalidVersion() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("number", "1.2abe");

        LessThanOp op = new LessThanOp();
        op.add("number");
        op.add("1");
        Assert.assertFalse(op.apply(attributes));
    }
}
