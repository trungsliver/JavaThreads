package concurrentCollection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {
    // Sức chứa tối đa của hàng đợi
    static final int QUEUE_CAPACITY = 10;
    // Hàng đợi để lưu trữ các phần tử
    static BlockingQueue taskQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

    public static void main(String[] args) {
        // Producer thread: Thêm phần tử vào hàng đợi (sản xuất)
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 20; i++) {
                    // Thêm phần tử vào hàng đợi
                    taskQueue.put(i);
                    System.out.println("Task produced: " + i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Consumer thread: Lấy phần tử từ hàng đợi (tiêu thụ)
        Thread consumer1 = new Thread(() -> {
            try {
                while (true) {
                    // Lấy phần tử từ hàng đợi
                    int task = (int) taskQueue.take();
                    processTask(task, "consumer1");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread consumer2 = new Thread(() -> {
            try {
                while (true) {
                    // Lấy phần tử từ hàng đợi
                    int task = (int) taskQueue.take();
                    processTask(task, "consumer2");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Bắt đầu các luồng
        producer.start();
        consumer1.start();
        consumer2.start();
    }

    private static void processTask(int task, String consumerName) throws InterruptedException {
        System.out.println("The task " + task + " is being processed by " + consumerName);
        // Giả lập thời gian xử lý công việc
        Thread.sleep(1000);
        System.out.println("Task comsumed by " + consumerName + ": " + task);
    }
}
