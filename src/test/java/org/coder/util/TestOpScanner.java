package org.coder.util;

import org.junit.Assert;
import org.junit.Test;

import org.coder.exception.UnsupportedOpException;
import org.coder.op.Op;
import org.coder.op.atomic.GreaterThanOp;

public class TestOpScanner {

    @Test
    public void testUnsupportedOp() {
        try {
            OpScanner.get("not exist op");
            Assert.fail("should throw exception if operation not supported");
        } catch (UnsupportedOpException e) {
        }
    }

    @Test
    public void testGetClassViaName() {
        Class<? extends Op> clz = OpScanner.get("gt");
        Assert.assertNotNull(clz);
        Assert.assertEquals(GreaterThanOp.class, clz);
    }

    @Test
    public void testGetNameViaClass() {
        String name = OpScanner.get(GreaterThanOp.class);
        Assert.assertEquals("gt", name);
    }
}
