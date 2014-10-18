package org.coder.op.composite;

import java.util.Map;

import org.coder.annotation.Operation;
import org.coder.op.Op;

/**
 * Operation same as "$$" semantic, check the given attributes
 * fit a set of rules.
 */
@Operation(name = "AND")
public class AndOp extends CompositeOp {

    /**
     * check whether the attributes fit all rules.
     *
     * @param attributes
     * @return true if all rules are fit.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        for (Op op : this) {
            if (!op.apply(attributes)) {
                return false;
            }
        }
        return true;
    }

    private static final long serialVersionUID = 3561282696121057680L;
}
