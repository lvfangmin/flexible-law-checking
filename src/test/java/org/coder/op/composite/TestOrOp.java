package org.coder.op.composite;

import org.coder.op.atomic.EqualOp;
import org.coder.op.atomic.GreaterThanOp;
import org.coder.op.atomic.common.UndefinedOp;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestOrOp {

    @Test
    public void testOrOp() {
        EqualOp eo = new EqualOp();
        eo.add("lang");
        eo.add("en");

        GreaterThanOp gto = new GreaterThanOp();
        gto.add("version");
        gto.add("1.0");

        UndefinedOp undefinedOp = new UndefinedOp();
        undefinedOp.add("timezone");

        OrOp op = new OrOp();
        op.add(eo);
        op.add(gto);
        op.add(undefinedOp);

        // 1. doesn't fit all OR rules
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("lang", "cn");
        attributes.put("version", "0.9");
        attributes.put("timezone", "GTM+8");

        Assert.assertFalse(op.apply(attributes));

        // 2. fit at least one of the rule
        attributes.remove("timezone");
        Assert.assertTrue(op.apply(attributes));
    }

}
