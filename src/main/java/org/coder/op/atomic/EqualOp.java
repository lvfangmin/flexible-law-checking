package org.coder.op.atomic;

import java.util.Map;

import org.coder.annotation.Operation;

/**
 * Compare the given attribute value with the value in the rule to
 * check whether they're equal.
 */
@Operation(name = "eq", requiredParamNum = 2)
public class EqualOp extends AtomicOp {

    /**
     * check whether the attribute are same as given in the rule.
     *
     * @param attributes
     * @return true if they're same.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {

        String attr = attributes.get(get(0));

        return attr != null && attr.equals(get(1));
    }

    private static final long serialVersionUID = 5856960332249336944L;
}
