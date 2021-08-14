package com.ttulka.samples;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class LoomMain {

    public static void main(String[] args) {
        new LoomMain().start();
    }

    public void start() {
        startServer(new FuturesServer());
        startServer(new LoomServer());
    }

    void startServer(Server server) {
        var startTime = System.nanoTime();
        System.out.println(server.getClass().getSimpleName() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        IntStream.range(0, 100_000)
            .mapToObj(i -> server.accept())
            .forEach(System.out::println);

        System.out.println("Execution time: " + (System.nanoTime() - startTime));
    }

    interface Server {
        String accept();
    }

    class FuturesServer implements Server {
        @Override
        public String accept() {
            try {
                return CompletableFuture
                    .supplyAsync(LoomMain::blockingString)
                    .thenApply(LoomMain::immediateString)
                    .get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class LoomServer implements Server {
        private final ExecutorService executor;

        public LoomServer() {
            executor = Executors.newVirtualThreadExecutor();
        }

        @Override
        public String accept() {
            return executor
                .submit(() -> {
                    var s1 = blockingString();
                    var s2 = immediateString(s1);
                    return s2;
                })
                .join();
        }
    }

    static String blockingString() {
        sleep(Duration.ofSeconds(2));
        return "Hello";
    }

    static String immediateString(String s) {
        return s + " World!";
    }

    static void sleep(Duration time) {
        try {
            Thread.sleep(time.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
