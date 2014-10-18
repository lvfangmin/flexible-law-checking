package org.coder.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import org.coder.annotation.Operation;
import org.coder.exception.UnsupportedOpException;
import org.coder.op.Op;

/**
 * Auto scan op folder to find all supported Operations. The purpose of this tool
 * is going to easy the work of adding new operations.
 *
 * The rule name to Op class mapping will be recorded when this class being loaded.
 */
public class OpScanner {

    // the folder which contains all Operation classes.
    private static final String OP_FOLDER = "org.coder.op";
    private static final Logger logger = LoggerFactory.getLogger(OpScanner.class);

    public static Map<String, Class<? extends Op>> operations;

    static Map<String, Class<? extends Op>> transform(Map<Operation, Class<? extends Op>> ops) {
        ImmutableMap.Builder<String, Class<? extends Op>> ret = ImmutableMap.builder();
        for (Map.Entry<Operation, Class<? extends Op>> entry : ops.entrySet()) {
            ret.put(trimLower(entry.getKey().name()), entry.getValue());
        }
        return ret.build();
    }

    static {
        try {
            /**
             * Scan the specified folder and load all Operations.
             * return a map contains <rule name, op class>
             */
            operations = transform(Reflection.scan(OP_FOLDER, Op.class, Operation.class));
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            logger.error("failed to scan operation classes", e);
        }
    }

    /**
     * Make all operation names case insensitive.
     *
     * @param name
     * @return trimmed to lower name.
     */
    static String trimLower(String name) {
        return name.toLowerCase().trim();
    }

    /**
     * Get the Operation class according to the rule name.
     *
     * @param name
     * @return the class of operation
     * @throw UnsupportedOpException if operation with the given name not found
     */
    public static Class<? extends Op> get(String name) {
        String _name = trimLower(name);
        if (!operations.containsKey(_name)) {
            throw new UnsupportedOpException();
        }

        return operations.get(_name);
    }

    /**
     * Get the rule name according to the Operation class.
     *
     * Could use bidirectional map to improve the performance if needed.
     *
     * @param op
     * @return the op name in rule
     * @throw UnsupportedOpException if operation with the given class not found
     */
    public static String get(Class<? extends Op> op) {
        for (Map.Entry<String, Class<? extends Op>> opEntry : operations.entrySet()) {
            if (opEntry.getValue() == op) {
                return opEntry.getKey();
            }
        }

        throw new UnsupportedOpException();
    }
}
