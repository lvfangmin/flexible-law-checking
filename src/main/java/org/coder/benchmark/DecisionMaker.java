package org.coder.benchmark;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.coder.Judge;
import org.coder.annotation.Benchmark;

/**
 * Measure the performance of @{link Op.apply} function.
 */
@Benchmark(
    id = "BenchDecisionCheck",
    description = "bench the latency of decision maker",
    args = "{ \"reps\":${RUNNING ROUND},\"rule\":${JSON RULE},\"data\":${JSON KEY VALUE OBJECT} }"
)
public class DecisionMaker implements Measurable {

    private Judge judge;
    private Params params;

    @Override
    public void init(String args) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        params = mapper.readValue(args, Params.class);

        judge = new Judge().init(mapper.writeValueAsString(params.getRule()));
    }

    @Override
    public void run() {
        for (int i = 0; i < params.getReps(); i++) {
            judge.apply(params.getData());
        }
    }

    public static class Params {
        private int reps;
        private Object rule;
        private Map<String, String> data;

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

        public void setData(HashMap<String, String> data) {
            this.data = data;
        }

        public Map<String, String> getData() {
            return data;
        }
    }
}
