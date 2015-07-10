package com.couchbase.client;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class CommandLineArgs {

    @Option(
        name = "-B",
        aliases = {"--batch-size"},
        usage = "Number of operations to batch [Default=100]"
    )
    private int batchSize = 100;

    @Option(
        name = "-I",
        aliases = {"--num-items"},
        usage = "Number of items to operate on [Default=1000]"
    )
    private int numItems = 1000;

    @Option(
        name = "-t",
        aliases = {"--num-threads"},
        usage = "The number of threads to use [Default=1]"
    )
    private int numThreads = 1;

    @Option(
        name = "-h",
        aliases = {"--help", "-?"},
        usage = "this message"
    )
    private boolean help = false;

    private final CmdLineParser parser;

    public CommandLineArgs(String[] args) {
        parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            parser.printUsage(System.err);
        }
    }

    public int batchSize() {
        return batchSize;
    }

    public int numItems() {
        return numItems;
    }

    public int numThreads() {
        return numThreads;
    }

    public boolean isHelp() {
        return help;
    }

    public void printHelp() {
        parser.printUsage(System.out);
    }

}
