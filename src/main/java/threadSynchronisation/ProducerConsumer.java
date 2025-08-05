package threadSynchronisation;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumer {
    public static void main(String[] args) {
        // Create a worker with a maximum of 10 items and a minimum of 5 items
        Worker worker = new Worker(5, 0);

        // Create producer and consumer threads
        Thread producer = new Thread(() -> {
            try {
                worker.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                System.out.println("Producer thread interrupted");
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                worker.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                System.out.println("Consumer thread interrupted");
            }
        });

        // Start the threads
        producer.start();
        consumer.start();
    }

}

class Worker{
    // Sequence number for the next produced item - số thứ tự cho sản phẩm tiếp theo
    private int sequence = 0;
    // The maximum number of items to produce - số lượng sản phẩm tối đa
    private final Integer top;
    // The minimum number of items to produce - số lượng sản phẩm tối thiểu
    private final Integer bottom;
    // The container to hold produced items - nơi chứa sản phẩm đã sản xuất
    private final List<Integer> container;
    // Lock object for synchronizing access to the container - đối tượng khóa để đồng bộ hóa truy cập vào nơi chứa
    private final Object lock = new Object();

    public Worker(Integer top, Integer bottom) {
        this.top = top;
        this.bottom = bottom;
        this.container = new ArrayList<>();
    }

    // Method to produce items - sản xuất
    public void produce() throws InterruptedException {
        synchronized (lock) {
            while(true){
                // Check if the container is full - kiểm tra xem nơi chứa có đầy không
                if (container.size() == top) {
                    System.out.println("Container is full, waiting for consumption...");
                    try {
                        lock.wait(); // Wait until an item is consumed - đợi cho đến khi một sản phẩm được tiêu thụ
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        System.out.println("Producer interrupted");
                    }
                } else {
                    container.add(sequence++); // Add the next item to the container - thêm sản phẩm tiếp theo vào nơi chứa
                    System.out.println("Produced: " + (sequence - 1));
                    lock.notify(); // Notify consumers that an item has been produced - thông báo cho người tiêu dùng rằng một sản phẩm đã được sản xuất
                }

                // Simulate time taken to produce an item - mô phỏng thời gian sản xuất
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while(true){
                if (container.size() == bottom) {
                    System.out.println("Container is empty, waiting for items to be added...");
                    try {
                        lock.wait(); // Wait until an item is produced - đợi cho đến khi một sản phẩm được sản xuất
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupted status
                        System.out.println("Consumer interrupted");
                    }
                } else {
                    System.out.println(container.removeFirst() + " remove from the container"); // Remove the first item from the container - lấy sản phẩm đầu tiên ra khỏi nơi chứa
                    lock.notify(); // Notify producers that an item has been consumed - thông báo cho người sản xuất rằng một sản phẩm đã được tiêu thụ
                }
                // Simulate time taken to consume an item - mô phỏng thời gian tiêu thụ
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
