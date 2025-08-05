package executorService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {
    public static void main(String[] args) {
        // 1000 tasks được xử lý với một pool có thể mở rộng (1000 threads)
        try (ExecutorService service = Executors.newCachedThreadPool()){
            for (int i = 0; i < 1000; i++) {
                // Each task will print its ID and the thread it is running on
                service.execute(new Task1(i));
            }
        }
    }

}

class Task1 implements Runnable {
    private final int taskId;

    public Task1(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Task with ID " + taskId + " is running in thread: " + Thread.currentThread().getName());
        try {
            // Simulate some work with sleep
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
        }
    }
}
