package org.coder;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coder.exception.JsonParseException;
import org.coder.exception.UnsupportedOpException;
import org.coder.op.Op;

/**
 * The main exported interface of Judge engine, constructed from a json rule String,
 * applied to the given data to check whether it fit the rules.
 */
public final class Judge {

    private final ObjectMapper mapper;
    private Op op;

    public Judge() {
        mapper = new ObjectMapper();
    }

    /**
     * init the Judge with the given rules.
     *
     * @param rules
     * @return the initialized Judge object.
     * @throws JsonParseException if the parse encountered any problem.
     *
     * NOTE: please check the guava version if your process or thread interrupted with
     *       weird behavior, please check @{link OpScanner} for more information.
     */
    public Judge init(String rules) throws JsonParseException {
        try {
            op = mapper.readValue(rules, Op.class);
        } catch (IOException | UnsupportedOpException e) {
            throw new JsonParseException(e);
        }

        return this;
    }

    /**
     * check whether given data fit the rules.
     *
     * @param data
     * @return true if fit.
     * @throws IllegalParamNumException if the operation's actual parameter
     *      size is not the same as expected.
     * @throws IllegalArgumentException if the attributes map is null.
     */
    public boolean apply(Map<String, String> attributes) {
        return op.apply(attributes);
    }

}
