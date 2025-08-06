package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;

// Ý nghĩa: Ví dụ về Semaphore với tính công bằng (FIFO)

// Mục đích: Đảm bảo các luồng lấy permit theo thứ tự yêu cầu, tránh tình trạng "starvation"

// Tình huống: Giả lập một hệ thống có 4 worker, mỗi worker sẽ cố gắng lấy permit từ Semaphore công bằng.

// Lợi ích: Giúp đảm bảo rằng các luồng sẽ được phục vụ theo thứ tự yêu cầu, tránh tình trạng một số luồng
            // bị bỏ qua hoặc chờ đợi quá lâu trong khi các luồng khác liên tục lấy permit.

// Cách sử dụng: Sử dụng Semaphore với tham số công bằng (true)
        // để đảm bảo rằng các luồng sẽ được phục vụ theo thứ tự yêu cầu (FIFO - First In, First Out).
        // Mỗi worker sẽ cố gắng lấy một "permit"
        // từ Semaphore trước khi thực hiện công việc của mình.
        // Khi hoàn thành, worker sẽ trả lại permit để các worker khác có thể tiếp tục công việc.

public class FairSemaphoreExample {
    static Semaphore semaphore = new Semaphore(1, true); // true: công bằng FIFO

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            Thread t = new Thread(new Worker(i));
            t.start();
        }
    }

    static class Worker implements Runnable {
        int id;

        Worker(int id) {
            this.id = id;
        }

        public void run() {
            try {
                System.out.println("🕒 Worker " + id + " đang chờ permit...");
                semaphore.acquire();
                System.out.println("✅ Worker " + id + " acquired permit.");

                Thread.sleep(2000);

                System.out.println("🔁 Worker " + id + " releasing permit.");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
