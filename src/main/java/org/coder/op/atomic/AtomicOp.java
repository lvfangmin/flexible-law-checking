package org.coder.op.atomic;

import org.coder.op.Items;
import org.coder.op.Op;

/**
 * Atomic Op is the minimum unit of Op, could not be constructed
 * by other Ops. Another characteristic: the items in this Op
 * are all pure String type.
 *
 * All Atomic Ops should extends this class to gain the Auto audit
 * function supported in the Aspect @{link OpAuditor}.
 */
public abstract class AtomicOp extends Items<String> implements Op {

    private static final long serialVersionUID = 6591377017784212207L;
}
