package org.coder.op.atomic.version;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestLessThanOp {

    @Test
    public void testLessThanOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2");

        // 1. test with 1.2 < 1.2.1 in version compare
        LessThanOp op = new LessThanOp();
        op.add("os-version");
        op.add(" 1.2.1 ");
        Assert.assertTrue(op.apply(attributes));

        // 2. test 'os-version' attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));

        // 3. test 1.2.4 < 1.10.1
        attributes.put("os-version", "1.2.4");
        op.set(1, "1.10.1");
        Assert.assertTrue(op.apply(attributes));

        // 4. test 1.2.4 < 2
        op.set(1, "2");
        Assert.assertTrue(op.apply(attributes));

        // 5. test 1.0 < 1.0.0.1
        op.set(1, "1.0.0.1");
        attributes.put("os-version", "1.0");
        Assert.assertTrue(op.apply(attributes));
    }

    @Test
    public void testLessThanOpWithSuffixZero() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2");

        // 1. test with 1.2 >= 1.2.0 in version compare
        LessThanOp op = new LessThanOp();
        op.add("os-version");
        op.add("1.2.0");
        Assert.assertFalse(op.apply(attributes));

        // 2. test 'os-version' attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));
    }

    @Test
    public void testLessThanOpWithInvalidVersion() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2abe");

        LessThanOp op = new LessThanOp();
        op.add("os-version");
        op.add("1.2.0");
        Assert.assertFalse(op.apply(attributes));
    }
}
