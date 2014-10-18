package org.coder.op.atomic.version;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class TestEqualOp {

    @Test
    public void testEqualOp() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2");

        // 1. test with 1.2 == " 1.2 " in version compare
        EqualOp op = new EqualOp();
        op.add("os-version");
        op.add(" 1.2 ");
        Assert.assertTrue(op.apply(attributes));

        // 2. test with different attribute
        op.set(1, "1.23.3");
        Assert.assertFalse(op.apply(attributes));
    }

    @Test
    public void testEqualOpWithSuffixZero() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2");

        // 1. test with 1.2 == 1.2.0 in version compare
        EqualOp op = new EqualOp();
        op.add("os-version");
        op.add("1.2.0");
        Assert.assertTrue(op.apply(attributes));

        // 2. test 'os-version' attribute not exist
        Assert.assertFalse(op.apply(new HashMap<String, String>()));
    }

    @Test
    public void testEqualOpWithInvalidVersion() {
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("os-version", "1.2abe");

        EqualOp op = new EqualOp();
        op.add("os-version");
        op.add("1.2.0");
        Assert.assertFalse(op.apply(attributes));
    }
}
