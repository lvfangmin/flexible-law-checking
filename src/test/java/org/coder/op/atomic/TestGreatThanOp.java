package org.coder.op.atomic;

import java.util.HashMap;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestGreatThanOp {

    @Test
    public void testGreatThan() {
        GreaterThanOp op = new GreaterThanOp();
        op.add("version");
        op.add("1.0");
        // 1. attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));

        // 2. attribute great than rule
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("version", "1.1");
        Assert.assertTrue(op.apply(attributes));

        // 3. attribute not great than rule
        attributes.put("version", "0.9");
        Assert.assertFalse(op.apply(attributes));
    }

}
