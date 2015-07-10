package com.couchbase.client;

import com.couchbase.client.java.Bucket;
import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;

public class Worker implements Runnable {

    private final Bucket bucket;
    private final CommandLineArgs args;
    private final Orchestrator orchestrator;

    public Worker(Orchestrator orchestrator, Bucket bucket, CommandLineArgs args) {
        this.bucket = bucket;
        this.args = args;
        this.orchestrator = orchestrator;
    }

    public void run() {
        Subscription subscription = Observable
            .range(1, args.batchSize())
            .flatMap(new Func1<Integer, Observable<?>>() {
                public Observable<?> call(Integer i) {
                    return bucket.async().get("id::" + i);
                }
            })
            .repeat()
            .subscribe();

        try {
            orchestrator.doneLatch().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            subscription.unsubscribe();
        }
    }
}
