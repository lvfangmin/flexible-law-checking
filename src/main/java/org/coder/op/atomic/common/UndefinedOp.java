package org.coder.op.atomic.common;

import org.coder.annotation.Operation;
import org.coder.op.atomic.AtomicOp;

import java.util.Map;

/**
 * Check whether the attribute does NOT contain certain key given in the rule.
 */
@Operation(name = "undefined", requiredParamNum = 1)
public class UndefinedOp extends AtomicOp {
    private static final long serialVersionUID = -4262224140083726408L;

    /**
     * @param attributes
     * @return true if attributes does Not contain the given attribute.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        // If attributes is Not set, it should also get an empty attributes instead of NULL.
        return !attributes.containsKey(get(0));
    }
}
