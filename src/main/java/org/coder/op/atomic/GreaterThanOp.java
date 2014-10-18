package org.coder.op.atomic;

import java.util.Map;

import org.coder.annotation.Operation;

/**
 * Compare the given attribute value with the value in the rule to
 * check the 'great or less' relationship.
 */
@Operation(name = "gt", requiredParamNum = 2)
public class GreaterThanOp extends AtomicOp {

    /**
     * check whether the attribute are greater than the value given in the rule.
     *
     * @param attributes
     * @return true if greater than the rule value.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {

        String attr = attributes.get(get(0));

        return attr != null && attr.compareTo(get(1)) > 0;
    }

    private static final long serialVersionUID = -6286578125004240085L;
}
