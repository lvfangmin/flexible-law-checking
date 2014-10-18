package org.coder.op.atomic.version;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestGreaterThanOp {

    @Test
    public void testGreaterThanOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.4");

        // 1. test with 1.4 > 1.3 in version compare
        GreaterThanOp op = new GreaterThanOp();
        op.add("os-version");
        op.add(" 1.3 ");
        Assert.assertTrue(op.apply(attributes));

        // 2. test 'os-version' attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));

        // 3. test 1.10.1 > 1.2.4
        attributes.put("os-version", "1.10.1");
        op.set(1, "1.2.4");
        Assert.assertTrue(op.apply(attributes));

        // 4. test 2 > 1.2.4
        attributes.put("os-version", "2");
        Assert.assertTrue(op.apply(attributes));

        // 5. test 2 not greater than 2.0.0
        op.set(1, "2.0.0");
        Assert.assertFalse(op.apply(attributes));

        // 6. test 1.0.0.1 > 1.0
        attributes.put("os-version", "1.0.0.1");
        op.set(1, "1.0");
        Assert.assertTrue(op.apply(attributes));
    }

    @Test
    public void testGreaterThanOpWithInvalidVersion() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2abe");

        GreaterThanOp op = new GreaterThanOp();
        op.add("os-version");
        op.add("1.2.0");
        Assert.assertFalse(op.apply(attributes));
    }
}
