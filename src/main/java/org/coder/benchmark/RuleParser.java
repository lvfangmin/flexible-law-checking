package org.coder.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coder.Judge;
import org.coder.annotation.Benchmark;
import org.coder.exception.JsonParseException;

/**
 * Measure the performance of @{link Op.init} function.
 */
@Benchmark(
    id = "BenchRuleParse",
    description = "bench the latency of rule parse",
    args = "{ \"reps\":${RUNNING ROUND},\"rule\":${JSON RULE} }"
)
public class RuleParser implements Measurable {

    private Params params;
    private String rule;

    @Override
    public void init(String args) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        params = mapper.readValue(args, Params.class);
        rule = mapper.writeValueAsString(params.getRule());
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < params.getReps(); i++) {
                new Judge().init(rule);
            }
        } catch (JsonParseException e) {
            System.err.println("Error while parsing rules: " + e.getMessage());
        }
    }

    public static class Params {
        private int reps;
        private Object rule;

        public void setReps(int reps) {
            this.reps = reps;
        }

        public int getReps() {
            return reps;
        }

        public void setRule(Object rule) {
            this.rule = rule;
        }

        public Object getRule() {
            return rule;
        }
    }
}
