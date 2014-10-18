package org.coder.op.composite;

import org.coder.op.Items;
import org.coder.op.Op;

/**
 * Composite Op is kind of Op which is constructed by one or more
 * Atomic Ops, different with @{link AtomicOp}, the items in this Op
 * are also Op object.
 */
public abstract class CompositeOp extends Items<Op> implements Op {

    private static final long serialVersionUID = 5037727844231104295L;
}
