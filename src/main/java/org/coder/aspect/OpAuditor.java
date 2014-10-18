package org.coder.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.coder.Constants;
import org.coder.annotation.Operation;
import org.coder.exception.IllegalParamNumException;
import org.coder.op.Items;
import org.coder.op.atomic.AtomicOp;

/**
 * An aspect used to easy the work of implementing Atomic operations.
 * All AtomicOp implementer only need to care the logical part.
 */
@Aspect
public class OpAuditor {

    /**
     * Catch the pointcut of Op+.apply function being called,
     * before calling the original apply function, audit will:
     *
     * <ul>
     *  <li> 1. check the operation parameter num </li>
     *  <li> 2. trim all items in operation </li>
     *  <li> 3. audit invalid arguments </li>
     * </ul>
     *
     * @param jp
     * @return the original value of apply
     * @throws IllegalParamNumException if the operations actual parameter
     *      size is not the same as expected.
     * @throws IllegalArgumentException if the attributes map is null.
     */
    @Around("call(* org.coder.op.Op+.apply(..))")
    public Object audit(ProceedingJoinPoint jp) throws Throwable {
        Object target = jp.getTarget();

        if (target instanceof AtomicOp) {
            AtomicOp op = (AtomicOp)target;
            int size = op.size();

            Operation opn = target.getClass().getAnnotation(Operation.class);
            // TODO: move the operation parameter checking to Judge initialize phrase.
            int requiredParamNums = opn.requiredParamNum();
            if (requiredParamNums != Constants.ANY && size != requiredParamNums) {
                throw new IllegalParamNumException();
            }

            trimItems(op, size);

            // check args
            Object[] args = jp.getArgs();
            if (args == null || args.length == 0 || args[0] == null) {
                throw new IllegalArgumentException();
            }
        }

        return jp.proceed();
    }

    /**
     * trim all items to make sure there is no typo mistake.
     *
     * NOTE: all the key/value pair in Rule Json String will be trimmed as we all agreed on,
     *       the attributes are trimmed in API component before wrote to storage.
     *       Sounds not like a good idea to put the trim logical into different modules.
     *
     * @param items
     * @param size
     */
    void trimItems(Items<String> items, int size) {
        for (int i = 0; i < size; i++) {
            items.set(i, items.get(i).trim());
        }
    }

}
