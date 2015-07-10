package com.couchbase.client;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Orchestrator {

    private final CommandLineArgs args;
    private final CountDownLatch done;
    private final Bucket bucket;
    private final ExecutorService workerPool;

    public Orchestrator(CommandLineArgs args) {
        this.args = args;
        this.done = new CountDownLatch(1);
        workerPool = Executors.newFixedThreadPool(args.numThreads());

        Cluster cluster = CouchbaseCluster.create();
        bucket = cluster.openBucket();
    }

    public CountDownLatch run() {

        // TODO: prepare data to load (like lcb)

        for (int i = 0; i < args.numThreads(); i++) {
            Worker worker = new Worker(this, bucket, args);
            workerPool.submit(worker);
        }

        // TODO: until user completes or the given time is over.
        try {
            workerPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return doneLatch();
    }

    public CountDownLatch doneLatch() {
        return done;
    }
}
