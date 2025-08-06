package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;

// Ý nghĩa: Ví dụ về Semaphore với nhiều permits

// Mục đích: Quản lý truy cập đồng thời vào tài nguyên dùng chung với số lượng permits lớn hơn 1

// Tình huống: Giả lập một hệ thống có 3 worker, mỗi worker cần một số lượng permits khác nhau để thực hiện công việc của nó.

// Lợi ích: Cho phép nhiều worker cùng truy cập tài nguyên dùng chung mà không bị chặn hoàn toàn,
            // đồng thời giới hạn số lượng worker có thể truy cập cùng lúc.

// Cách sử dụng: Sử dụng Semaphore với số lượng permits lớn hơn 1 để quản lý truy cập đồng thời vào tài nguyên dùng chung.
            // Mỗi worker sẽ yêu cầu một số lượng permits nhất định từ Semaphore trước khi thực hiện công việc của mình.
            // Khi hoàn thành, worker sẽ trả lại số lượng permits đã sử dụng để các worker khác có thể tiếp tục công việc.
            // Ví dụ này mô phỏng một hệ thống có 3 worker, mỗi worker yêu cầu một số lượng permits khác nhau (3, 2, và 1) từ Semaphore có tổng số permits là 5.
            // Điều này cho phép các worker có thể truy cập tài nguyên dùng chung


public class MultiPermitExample {
    static Semaphore semaphore = new Semaphore(5); // Tổng 5 permits

    public static void main(String[] args) {
        Thread t1 = new Thread(new Task("T1", 3));
        Thread t2 = new Thread(new Task("T2", 2));
        Thread t3 = new Thread(new Task("T3", 1));
        t1.start();
        t2.start();
        t3.start();
    }

    static class Task implements Runnable {
        private String name;
        private int permitsNeeded;

        public Task(String name, int permitsNeeded) {
            this.name = name;
            this.permitsNeeded = permitsNeeded;
        }

        public void run() {
            try {
                System.out.println(name + " yêu cầu " + permitsNeeded + " permits.");
                semaphore.acquire(permitsNeeded);
                System.out.println(name + " đã lấy " + permitsNeeded + " permits.");

                Thread.sleep(3000);

                System.out.println(name + " trả lại " + permitsNeeded + " permits.");
                semaphore.release(permitsNeeded);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
