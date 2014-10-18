package org.coder.op.atomic.version;

import java.util.Map;

import org.coder.op.atomic.AtomicOp;
import org.coder.annotation.Operation;

/**
 * Compare the given attribute value about version number with the value in the rule to
 * check whether they're equal.
 */
@Operation(name = "veq", requiredParamNum = 2)
public class EqualOp extends AtomicOp {
    /**
     * check whether the version in attribute are same as given in the rule.
     *
     * @param attributes
     * @return true if they're equal.
     */
    @Override
    public boolean apply(Map<String, String> attributes) {
        Version version = new Version(attributes.get(get(0)));

        try {
            return !version.isEmpty() && version.compareTo(new Version(get(1))) == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static final long serialVersionUID = 6220898620615574343L;
}
