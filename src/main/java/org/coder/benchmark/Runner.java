package org.coder.benchmark;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.coder.annotation.Benchmark;
import org.coder.util.Reflection;

/**
 * Usage:
 * mvn compile exec:java -Dexec.mainClass="org.coder.benchmark.Runner"
 * -Dexec.args=
 * "--BenchDecisionCheck::'{\"reps\":10000,\"rule\":{\"eq\":[\"lang\",\"en\"]},\"data\":{\"lang\":\"en\"}}' "
 *
 * Benchmark result:
 * <ul>
 *   <li> 1. json parse avg cost: 0.3ms </li>
 *   <li> 2. filter avg cost: 0.4ns </li>
 * </ul>
 */
public class Runner {

    private static final String BENCHMARK_FOLDER = "org.coder.benchmark";
    private static Map<String, Class<? extends Measurable>> benchClz;
    private static List<String> usage;

    static {
        try {
            Map<Benchmark, Class<? extends Measurable>> clz =
                Reflection.scan(BENCHMARK_FOLDER, Measurable.class, Benchmark.class);
            benchClz = transform(clz);
            usage = buildUsage(clz);
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            System.err.println("Error while load classes: " + e.getMessage());
        }
    }

    static Map<String, Class<? extends Measurable>> transform(
            Map<Benchmark, Class<? extends Measurable>> clz) {
        ImmutableMap.Builder<String, Class<? extends Measurable>> benchBuilder = ImmutableMap.builder();
        for (Map.Entry<Benchmark, Class<? extends Measurable>> entry : clz.entrySet()) {
            benchBuilder.put(entry.getKey().id(), entry.getValue());
        }
        return benchBuilder.build();
    }

    static List<String> buildUsage(Map<Benchmark, Class<? extends Measurable>> clz) {
        final int MIN_LENGTH = 29;
        ImmutableList.Builder<String> usageBuilder = ImmutableList.builder();
        usageBuilder.addAll(USAGE_HEADER);
        for (Benchmark mark : clz.keySet()) {
            usageBuilder.add("   " + Strings.padEnd(mark.id(), MIN_LENGTH, ' ') + mark.description());
            usageBuilder.add("   " + Strings.padEnd("Args Format:", MIN_LENGTH, ' ') + mark.args());
            usageBuilder.add("");
        }
        return usageBuilder.build();
    }

    static void showHelpAndExit() {
        for(String line : usage) {
            System.err.println(line);
        }
        System.exit(-1);
    }

    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("h", "help", false, "Show usage");
        options.addOption("b", "bench", true, "Select benchmark to be run");

        CommandLineParser parser = new PosixParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("h") || !line.hasOption("b")) {
            showHelpAndExit();
        }

        String bargs = line.getOptionValue("b");
        List<String> items = Lists.newArrayList(Splitter.on("::").trimResults().omitEmptyStrings().split(bargs));
        if (items.size() != 2 || !benchClz.containsKey(items.get(0))) {
            System.err.println("[ERROR] Not enough arguments, or the bench class not exist!\n");
            showHelpAndExit();
        }

        Class<? extends Measurable> bc = benchClz.get(items.get(0));
        try {
            Measurable bench = bc.newInstance();
            bench.init(items.get(1));
            bench.run();
        } catch (Throwable t) {
            System.err.println("[ERROR] Failed to run benchmark with error: " + t.getMessage());
        }
    }

    private static final ImmutableList<String> USAGE_HEADER = ImmutableList.of(
            "Usage: Runner <command>",
            "",
            "   -h, --help                    print help messages",
            "   -b, --bench <id>::<args>      the benchmark id to be tested",
            "",
            "Bench id:",
            ""
            );
}
