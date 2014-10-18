package org.coder.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Measure the time cost in the benchmark.
 */
@Aspect
public class BenchmarkTimer {

    void write(String message) {
        System.err.println(message);
    }

    @Around("call(* org.coder.benchmark.Measurable+.run(..))")
    public Object timer(ProceedingJoinPoint jp) throws Throwable {
        write("-------------------------------------------");
        write("Running benchmark:");

        long start = System.currentTimeMillis();
        Object ret = jp.proceed();
        long duration = System.currentTimeMillis() - start;

        write("total cost " + duration + "ms");
        write("-------------------------------------------");

        return ret;
    }
}
