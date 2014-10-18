package org.coder;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.coder.exception.JsonParseException;

public class TestJudge {

    @Test
    public void testMalformedRules() {
        String rules = "{\"unknown\":\"what\"}";
        try {
            new Judge().init(rules);
            Assert.fail("should throw exception with malformed rules");
        } catch (JsonParseException e) {
        }
    }

    @Test
    public void testJudge() throws JsonParseException {
        String rules = "{\"AND\":[{\"gt\":[\"ver\", \"1\"]}, {\"eq\":[\"lang\", \"en\"]}, {\"AND\":[{\"eq\":[\"name\", \"test\"]}]} ]}";
        Judge judge = new Judge().init(rules);

        Map<String, String> attributes = new HashMap<String, String>();

        // 1. attributes not exist
        Assert.assertFalse(judge.apply(attributes));

        // 2. fit all attributes
        attributes.put("ver", "2");
        attributes.put("lang", "en");
        attributes.put("name", "test");
        Assert.assertTrue(judge.apply(attributes));

        // 3. one of them doesn't fit
        attributes.put("name", "noname");
        Assert.assertFalse(judge.apply(attributes));
    }

}
