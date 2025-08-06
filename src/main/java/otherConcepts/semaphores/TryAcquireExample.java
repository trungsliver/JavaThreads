package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;

// ý nghĩa: Ví dụ về Semaphore với tryAcquire

// Mục đích: Cho phép luồng cố gắng lấy permit mà không bị chặn nếu không có permit sẵn

// Tình huống: Giả lập một hệ thống có 2 luồng, mỗi luồng cố gắng lấy permit từ Semaphore.

// Lợi ích: Giúp tránh tình trạng chờ đợi vô hạn nếu không có permit sẵn,
            // cho phép luồng thực hiện các hành động khác nếu không lấy được permit.

// Cách sử dụng:
    // Sử dụng phương thức tryAcquire() của Semaphore để cố gắng lấy permit mà không bị chặn.
    // Nếu không có permit sẵn, luồng sẽ nhận được giá trị false và có thể thực hiện các hành động khác hoặc thông báo không lấy được permit.
    // Ví dụ này mô phỏng một hệ thống có 2 luồng, mỗi luồng cố gắng lấy permit từ Semaphore có 1 permit.
    // Luồng đầu tiên (T1) sẽ lấy permit và giữ trong 2 giây, trong khi luồng thứ hai (T2) sẽ cố gắng lấy permit
            // nhưng sẽ không thể lấy được vì T1 đang giữ permit đó.
    // Điều này cho thấy cách sử dụng Semaphore với tryAcquire để quản lý truy cập đồng thời vào tài nguyên dùng chung
            // mà không bị chặn hoàn toàn nếu không có permit sẵn.



public class TryAcquireExample {
    static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            if (semaphore.tryAcquire()) {
                try {
                    System.out.println("✅ Thread T1 acquired permit.");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    System.out.println("🔴 Thread T1 released permit.");
                }
            } else {
                System.out.println("⚠️ Thread T1 couldn't acquire permit.");
            }
        });

        Thread t2 = new Thread(() -> {
            if (semaphore.tryAcquire()) {
                try {
                    System.out.println("✅ Thread T2 acquired permit.");
                } finally {
                    semaphore.release();
                }
            } else {
                System.out.println("⚠️ Thread T2 couldn't acquire permit.");
            }
        });

        t1.start();
        t2.start();
    }
}
