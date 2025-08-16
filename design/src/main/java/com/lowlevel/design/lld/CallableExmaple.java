package com.lowlevel.design.lld;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExmaple {
    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Callable<String> task = new MyCallable();
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return task.call();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }, executor);

            future.thenApply(a-> {return a + "Something";})
                            .thenApply(a-> {return a.indexOf("thing");} )
                            .thenApply(a-> {
                                return a + 1000;
                            })
                            .thenAccept(a->{System.out.println(a);});

            executor.shutdown();
        } catch (Exception ignored) { }
    }

    static class MyCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            // Simulate some work
            Thread.sleep(1000);
            return "Task completed";
        }
    }


}
