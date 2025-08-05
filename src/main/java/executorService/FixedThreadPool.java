package executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {
    public static void main(String[] args) {
        // xử lý 7 tasks với 2 threads trong pool
        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            // Submit tasks to the executor service
            for (int i = 0; i < 7; i++) {
                // Each task will print its ID and the thread it is running on
                service.execute(new Work(i));
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            System.out.println("Executor service has been shut down.");

        }
    }
}

class Work implements  Runnable {
    private final int workId;

    public Work(int workId) {
        this.workId = workId;
    }

    @Override
    public void run() {
        System.out.println("Task with ID " + workId + " is running in thread: " + Thread.currentThread().getName());
        try {
            // Simulate some work with sleep
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }
    }
}
