package com.couchbase.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Jellofight {

    public static void main(String[] args) throws Exception {
        banner();

        CommandLineArgs cliArgs = new CommandLineArgs(args);
        if (cliArgs.isHelp()) {
            cliArgs.printHelp();
            return;
        }

        Orchestrator orchestrator = new Orchestrator(cliArgs);
        CountDownLatch latch = orchestrator.run();
        latch.await(1, TimeUnit.DAYS);
    }

    private static void banner() {
        System.out.println("Jellofight 1.0.0 by Couchbase, Inc.");
        System.out.println("Benchmarking is hard, naming is harder.\n");
    }

}
