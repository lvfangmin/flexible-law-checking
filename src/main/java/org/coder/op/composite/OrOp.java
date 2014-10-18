package org.coder.op.composite;

import java.util.Map;

import org.coder.annotation.Operation;
import org.coder.op.Op;

/**
 * Operation same as "||" semantic, check the given attributes
 * fit one of the rule defined in the rule set.
 */
@Operation(name = "OR")
public class OrOp extends CompositeOp {

    /**
     * check whether the attributes fit one of the rule in given rule set.
     *
     * @param attributes
     * @return true if fit at least one of the rule.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        for (Op op : this) {
            if (op.apply(attributes)) {
                return true;
            }
        }
        return false;
    }

    private static final long serialVersionUID = 463172149125528024L;
}
