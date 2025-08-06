package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;

// Ý nghĩa: Ví dụ đơn giản về Semaphore trong Java

// Mục đích: Giới hạn số lượng luồng truy cập vào tài nguyên dùng chung

// Tình huống: Giả lập một hệ thống có 6 worker, nhưng chỉ cho phép 3 worker cùng lúc truy cập vào tài nguyên dùng chung.

// Lợi ích: Giúp tránh tình trạng quá tải tài nguyên, đảm bảo hiệu suất và ổn định của hệ thống.

// Cách sử dụng: Sử dụng Semaphore để quản lý số lượng luồng có thể truy cập vào tài nguyên dùng chung.
        // Mỗi worker sẽ cố gắng lấy một "permit" từ Semaphore trước khi thực hiện công việc của mình.
        // Khi hoàn thành, worker sẽ trả lại permit để các worker khác có thể tiếp tục công việc.


public class BasicSemaphoreExample {
    static Semaphore semaphore = new Semaphore(3); // Chỉ cho phép 3 thread cùng lúc

    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) {
            Thread t = new Thread(new Worker(i));
            t.start();
        }
    }

    static class Worker implements Runnable {
        private int id;

        public Worker(int id) {
            this.id = id;
        }

        public void run() {
            try {
                System.out.println("🟡 Worker " + id + " đang chờ permit...");
                semaphore.acquire(); // Chờ permit
                System.out.println("✅ Worker " + id + " đã lấy permit");

                Thread.sleep(2000); // Giả lập công việc mất thời gian

                System.out.println("🔴 Worker " + id + " trả lại permit");
                semaphore.release(); // Trả lại permit
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
