import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class LoomMain {

    final static String LONG_DATA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam scelerisque elit non ornare rhoncus. Quisque hendrerit congue nulla. Aenean justo tellus, venenatis ullamcorper magna eu, rhoncus laoreet orci. Pellentesque ac euismod lectus. Phasellus efficitur, libero iaculis accumsan efficitur, ante felis sagittis dui, at dignissim velit elit in orci. Aenean porttitor elementum nunc, ac scelerisque felis tempor in. Aenean elementum laoreet urna vitae dictum. Maecenas vestibulum tempus gravida.\n\nCras ac mattis ipsum, a iaculis ligula. Mauris accumsan sodales vulputate. Curabitur pellentesque purus nec turpis convallis, sed convallis dolor auctor. Proin vehicula sit amet ex ac vehicula. Nunc ultrices ultricies ultrices. Aliquam erat volutpat. Praesent vitae faucibus mauris, vitae accumsan velit. Quisque tristique aliquam nulla. Phasellus in interdum velit. Aliquam erat volutpat. Praesent et dolor turpis. Donec faucibus, ligula eu scelerisque pharetra, nibh enim commodo magna, nec fringilla risus augue quis sapien. Aliquam at egestas arcu. Morbi ipsum leo, bibendum quis vulputate sed, sodales sit amet arcu.\n\nQuisque a elit ac ipsum finibus maximus. Maecenas eu orci dui. In sed vehicula elit, nec ullamcorper ligula. Fusce maximus eu felis eget rhoncus. Nunc non bibendum lorem. Nulla condimentum auctor lorem, non molestie nisl posuere ac. Sed suscipit facilisis leo eu interdum. In faucibus elementum mi. Donec nec lobortis turpis, id feugiat dui. Donec finibus sodales sapien, vitae bibendum quam condimentum eget. Aliquam convallis mollis dui, vehicula commodo sapien.\n\nNullam vitae risus tempor, tincidunt urna tempus, semper ex. In nec pellentesque erat, in euismod felis. Vestibulum semper magna id libero dignissim malesuada. Morbi velit dolor, viverra vitae augue id, imperdiet placerat purus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Curabitur eleifend aliquet elit quis gravida. Morbi dictum accumsan dolor, quis auctor ante eleifend a.\n\nDonec facilisis placerat lorem, sit amet vestibulum arcu blandit quis. Phasellus ultricies id ipsum id mattis. Suspendisse consectetur eleifend nulla ac aliquet. Sed venenatis, enim quis dapibus dapibus, nibh sem luctus erat, at efficitur orci tellus nec ipsum. Cras nunc odio, placerat eu congue quis, semper a nisi. Morbi eu lorem cursus, pellentesque leo non, consequat sem. In semper pretium dictum. Mauris facilisis aliquet neque.";

    public static void main(String[] args) {
        new LoomMain().start();
    }

    public void start() {
        startServer(new ThreadProcessor());
        startServer(new FuturesProcessor());
        startServer(new LoomProcessor());
    }

    void startServer(Processor processor) {
        var startTime = System.currentTimeMillis();
        System.out.println(processor.getClass().getSimpleName() + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>");

        processor.process(100_000);

        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + " ms");
    }

    interface Processor {
        void process(int count);

        default String nonblockingString(int i) {
            return Math.cos(i) + " Hello";
        }

        default String blockingString(int i, String s) {
            var content = s + " World!";
            try {
                Files.write(
                        Path.of(getClass().getSimpleName() + (i % 100) + ".txt"),
                        (content + LONG_DATA).getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }
    }

    class ThreadProcessor implements Processor {
        @Override
        public void process(int count) {
            var counter = new AtomicInteger();
            try (ExecutorService executor = Executors.newCachedThreadPool()) {
                IntStream.range(0, count).forEach(i ->
                    executor.submit(() -> {
                        var s1 = nonblockingString(i);
                        var s2 = blockingString(i, s1);
                        counter.incrementAndGet();
                        return s2;
                    })
                );
            }
            System.out.println("Threads counter: " + counter.get());
        }
    }

    class FuturesProcessor implements Processor {
        @Override
        public void process(int count) {
            var counter = new AtomicInteger();
            try (ExecutorService executor = Executors.newCachedThreadPool()) {
                IntStream.range(0, count).forEach(i -> {
                    try {
                        CompletableFuture
                                .supplyAsync(() -> nonblockingString(i), executor)
                                .thenApply(s -> blockingString(i, s))
                                .thenRun(counter::incrementAndGet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            System.out.println("Futures counter: " + counter.get());
        }
    }

    class LoomProcessor implements Processor {
        @Override
        public void process(int count) {
            var counter = new AtomicInteger();
            try (ExecutorService executor = Executors.newVirtualThreadExecutor()) {
                IntStream.range(0, count).forEach(i ->
                    executor.submit(() -> {
                        var s1 = nonblockingString(i);
                        var s2 = blockingString(i, s1);
                        counter.incrementAndGet();
                        return s2;
                    })
                );
            }
            System.out.println("Loom counter: " + counter.get());
        }
    }

    static void sleep(Duration time) {
        try {
            Thread.sleep(time.toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
