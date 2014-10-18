package org.coder.op.atomic.number;

import java.util.Map;

import org.coder.annotation.Operation;
import org.coder.op.atomic.AtomicOp;

/**
 * Compare the given attribute value with the value in the rule to check the
 * 'greater or less' relationship.
 */
@Operation(name = "<", requiredParamNum = 2)
public class LessThanOp extends AtomicOp {
    /**
     * check whether the attribute are less than given in the rule.
     *
     * @param attributes
     * @return true if they're less than number in rule
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        Number number = new Number(attributes.get(get(0)));

        try {
            return number.compareTo(new Number(get(1))) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static final long serialVersionUID = -3262224140072521389L;
}
