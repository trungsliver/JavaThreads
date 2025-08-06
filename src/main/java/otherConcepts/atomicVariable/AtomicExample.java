package otherConcepts.atomicVariable;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    // Non-atomic variable
    static int count1 = 0;
    // Atomic variable
    static AtomicInteger count2 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                count1++;                   // Non-thread-safe increment
                count2.incrementAndGet();   // Thread-safe increment
            }
        };
        // Khởi tạo 2 luồng để thực hiện cùng một tác vụ
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        // Bắt đầu 2 luồng
        t1.start();
        t2.start();
        // Chờ cho cả 2 luồng hoàn thành
        t1.join();
        t2.join();

        // Hiển thị kết quả
        System.out.println("Giá trị của count1 (non-atomic): " + count1);
        System.out.println("Giá trị của count2 (atomic): " + count2.get());
    }
}
