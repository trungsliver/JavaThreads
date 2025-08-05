package executorService;

import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CpuIntensiveTask {
    public static void main(String[] args) {
        // số lượng lõi CPU hiện có trên máy tính
        int cores = Runtime.getRuntime().availableProcessors();

        ExecutorService service = Executors.newFixedThreadPool(cores);
        // Submit CPU intensive tasks to the executor service
        System.out.println("Number of available CPU cores: " + cores);
        System.out.println("Created thread pool with " + cores + " threads.");

        for (int i = 0; i < 20; i++) {
            service.execute(new CpuTask());
        }
    }
}

class CpuTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Some CPU intensive task being done by: " + Thread.currentThread().getName());

    }
}