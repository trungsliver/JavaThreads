package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// Ý nghĩa: Ví dụ về Semaphore với tryAcquire và timeout

// Mục đích: Cho phép luồng cố gắng lấy permit trong một khoảng thời gian nhất định, tránh tình trạng chờ đợi vô hạn

// Tình huống: Giả lập một hệ thống có 2 luồng, trong đó một luồng (T1) giữ permit trong 4 giây,
            // và luồng thứ hai (T2) cố gắng lấy permit trong 2 giây.

// Lợi ích: Giúp tránh tình trạng chờ đợi vô hạn nếu không có permit sẵn,
            // cho phép luồng thực hiện các hành động khác hoặc thông báo không lấy được permit nếu quá thời gian chờ.

// Cách sử dụng:
    // Sử dụng phương thức tryAcquire(long timeout, TimeUnit unit) của Semaphore để cố gắng lấy permit trong một khoảng thời gian nhất định.
    // Nếu không có permit sẵn trong khoảng thời gian đó, luồng sẽ nhận được giá trị false và có thể thực hiện các hành động khác
        // hoặc thông báo không lấy được permit.
    // Ví dụ này mô phỏng một hệ thống có 2 luồng, trong đó T1 sẽ lấy permit và giữ trong 4 giây, trong khi T2 sẽ cố gắng lấy permit trong 2 giây.
    // Nếu T2 không lấy được permit trong khoảng thời gian đó, nó sẽ thông báo rằng không thể lấy được permit.
    // Điều này cho thấy cách sử dụng Semaphore với tryAcquire và timeout để quản lý truy cập đồng thời vào tài nguyên dùng chung
        // mà không bị chặn hoàn toàn nếu không có permit sẵn trong một khoảng thời gian nhất định.

public class TryAcquireWithTimeoutExample {
    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println("✅ T1 acquired permit.");
                Thread.sleep(4000); // Giữ permit 4 giây
                semaphore.release();
                System.out.println("🔴 T1 released permit.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                System.out.println("T2 đang đợi permit trong 2 giây...");
                if (semaphore.tryAcquire(2, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("✅ T2 acquired permit.");
                    } finally {
                        semaphore.release();
                    }
                } else {
                    System.out.println("❌ T2 timeout, không lấy được permit.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        Thread.sleep(100); // T1 chiếm trước permit
        t2.start();
    }
}
