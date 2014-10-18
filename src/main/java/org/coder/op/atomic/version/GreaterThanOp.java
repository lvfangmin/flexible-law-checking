package org.coder.op.atomic.version;

import java.util.Map;

import org.coder.annotation.Operation;
import org.coder.op.atomic.AtomicOp;

/**
 * Compare the given attribute value with the value in the rule to
 * check the 'great or less' relationship.
 */
@Operation(name = "vgt", requiredParamNum = 2)
public class GreaterThanOp extends AtomicOp {

    /**
     * check whether the version in attributes are greater than the version value given in the rule.
     *
     * @param attributes
     * @return true if greater than the rule value.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        Version version = new Version(attributes.get(get(0)));

        try {
            return !version.isEmpty() && version.compareTo(new Version(get(1))) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static final long serialVersionUID = -7878211544409728899L;
}
