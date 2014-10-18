package org.coder.benchmark;

/**
 * All benchmarks should implement this interface.
 */
public interface Measurable {

    /**
     * Init the Benchmark class.
     *
     * @param args
     * @throws Throwable
     */
    public abstract void init(String args) throws Throwable;

    /**
     * Execute the benchmark process.
     */
    public abstract void run();
}
