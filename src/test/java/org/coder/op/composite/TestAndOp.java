package org.coder.op.composite;

import org.coder.op.atomic.EqualOp;
import org.coder.op.atomic.GreaterThanOp;
import org.coder.op.atomic.common.UndefinedOp;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestAndOp {

    @Test
    public void testAndOp() {
        EqualOp eo = new EqualOp();
        eo.add("lang");
        eo.add("en");

        GreaterThanOp gto = new GreaterThanOp();
        gto.add("version");
        gto.add("1.0");

        UndefinedOp undefinedOp = new UndefinedOp();
        undefinedOp.add("timezone");

        AndOp op = new AndOp();
        op.add(eo);
        op.add(gto);
        op.add(undefinedOp);

        // 1. test fit all AND rules
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("lang", "en");
        attributes.put("version", "1.1");

        Assert.assertTrue(op.apply(attributes));

        // 2. test one of them doesn't fit
        attributes.put("lang", "cn");
        Assert.assertFalse(op.apply(attributes));
    }

}
