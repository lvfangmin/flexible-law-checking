package org.coder.op.atomic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestLessThanOp {

    @Test
    public void testLessThan() {
        LessThanOp op = new LessThanOp();
        op.add("version");
        op.add("1.0");

        // 1. attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));

        // 2. attribute less than rule
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("version", "0.9");
        Assert.assertTrue(op.apply(attributes));

        // 3. attribute not less than rule
        attributes.put("version", "1.1");
        Assert.assertFalse(op.apply(attributes));
    }

}
