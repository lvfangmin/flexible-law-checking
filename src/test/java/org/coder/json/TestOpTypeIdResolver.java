package org.coder.json;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coder.op.Items;
import org.coder.op.Op;
import org.coder.op.atomic.*;
import org.coder.op.composite.*;

public class TestOpTypeIdResolver {
    private static final String RULES = "{\"AND\":[{\"gt\":[\"ver\", \"1\"]}, {\"eq\":[\"lang\", \"en\"]}, {\"AND\":[{\"eq\":[\"name\", \"test\"]}]} ]}";

    @Test @SuppressWarnings("unchecked")
    public void testPolymorphicOpResolver() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Op o = mapper.readValue(RULES, Op.class);
        Assert.assertTrue(o instanceof Items);

        Items<Op> items = (Items<Op>)o;

        Assert.assertEquals(3, items.size());

        for (Op op : items) {
            if (op instanceof GreaterThanOp) {
                checkGtOp(op);
            } else if (op instanceof EqualOp) {
                checkLtOp(op);
            } else if (op instanceof AndOp) {
                checkAndOp(op);
            }
        }
    }

    @SuppressWarnings("unchecked")
    void checkGtOp(Op op) {
        Items<String> o = (Items<String>) op;
        Assert.assertEquals(2, o.size());
        Assert.assertEquals("ver", o.get(0));
        Assert.assertEquals("1", o.get(1));
    }

    @SuppressWarnings("unchecked")
    void checkLtOp(Op op) {
        Items<String> o = (Items<String>) op;
        Assert.assertEquals(2, o.size());
        Assert.assertEquals("lang", o.get(0));
        Assert.assertEquals("en", o.get(1));
    }

    @SuppressWarnings("unchecked")
    void checkAndOp(Op op) {
        Items<Op> o = (Items<Op>) op;
        Assert.assertEquals(1, o.size());
        Assert.assertTrue(o.get(0) instanceof EqualOp);

        Items<String> eo = (Items<String>) o.get(0);
        Assert.assertEquals(2, eo.size());
        Assert.assertEquals("name", eo.get(0));
        Assert.assertEquals("test", eo.get(1));
    }
}
