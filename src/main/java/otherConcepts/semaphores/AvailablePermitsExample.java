package otherConcepts.semaphores;

import java.util.concurrent.Semaphore;

// Ý nghĩa: Ví dụ về cách sử dụng phương thức availablePermits() của Semaphore

// Mục đích: Hiển thị số lượng permits hiện có trong Semaphore tại thời điểm

// Tình huống: Giả lập một hệ thống có 3 permits ban đầu, trong đó một luồng sẽ lấy 1 permit,
    // sau đó một luồng khác sẽ lấy thêm 2 permits, và cuối cùng sẽ trả lại 3 permits.

// Lợi ích: Giúp hiểu rõ cách thức hoạt động của Semaphore và số lượng permits có sẵn tại mỗi thời điểm,
    // từ đó quản lý truy cập đồng thời vào tài nguyên dùng chung.

// Cách sử dụng:
    // Sử dụng phương thức availablePermits() để kiểm tra số lượng permits hiện có trong Semaphore.
    // Phương thức này trả về số lượng permits mà Semaphore còn lại, cho phép người dùng theo dõi và quản lý truy cập đồng thời vào tài nguyên dùng chung.


public class AvailablePermitsExample {
    static Semaphore semaphore = new Semaphore(3);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Số permits ban đầu: " + semaphore.availablePermits());

        semaphore.acquire();
        System.out.println("Sau acquire 1 permit: " + semaphore.availablePermits());

        semaphore.acquire(2);
        System.out.println("Sau acquire thêm 2 permits: " + semaphore.availablePermits());

        semaphore.release(3);
        System.out.println("Sau release 3 permits: " + semaphore.availablePermits());
    }
}

