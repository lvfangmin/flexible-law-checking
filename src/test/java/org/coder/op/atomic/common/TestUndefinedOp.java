package org.coder.op.atomic.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestUndefinedOp {
    @Test
    public void testUndefinedOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("exist", "1");

        UndefinedOp op = new UndefinedOp();
        op.add("timezone");
        // 1. attributes does Not contain key "timezone" given by rule
        Assert.assertTrue(op.apply(attributes));

        attributes.put("timezone", "GTM");
        // 2. attributes contain key "timezone" given by rule
        Assert.assertFalse(op.apply(attributes));

        // 3. attributes is empty
        Assert.assertTrue(op.apply(new HashMap<String, String>()));
    }
}
