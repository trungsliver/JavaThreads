package locks.readWriteLock; // Khai báo package chứa class này

import java.util.concurrent.locks.Lock; // Import interface Lock để sử dụng khóa
import java.util.concurrent.locks.ReadWriteLock; // Import interface ReadWriteLock cho khóa đọc/ghi
import java.util.concurrent.locks.ReentrantReadWriteLock; // Import lớp ReentrantReadWriteLock cho phép nhiều luồng đọc, một luồng ghi

public class ReadWriteExample {
    // Khởi tạo đối tượng ReadWriteLock để quản lý truy cập đồng thời
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    // Tạo khóa đọc từ ReadWriteLock
    private final Lock readLock = rwLock.readLock();
    // Tạo khóa ghi từ ReadWriteLock
    private final Lock writeLock = rwLock.writeLock();

    // Biến dùng chung giữa các luồng
    private int sharedData = 0;

    // Phương thức đọc dữ liệu với tên luồng truyền vào
    public void readData(String threadName) {
        readLock.lock(); // Khóa đọc, cho phép nhiều luồng đọc đồng thời
        try {
            System.out.println(threadName + " đang đọc dữ liệu: " + sharedData); // In ra dữ liệu đang đọc
            Thread.sleep(100); // Mô phỏng thời gian đọc
        } catch (InterruptedException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu bị gián đoạn
        } finally {
            readLock.unlock(); // Mở khóa đọc sau khi đọc xong
        }
    }

    // Phương thức ghi dữ liệu với tên luồng và giá trị truyền vào
    public void writeData(String threadName, int value) {
        writeLock.lock(); // Khóa ghi, chỉ cho phép một luồng ghi tại một thời điểm
        try {
            System.out.println(threadName + " đang ghi dữ liệu: " + value); // In ra dữ liệu đang ghi
            Thread.sleep(200); // Mô phỏng thời gian ghi
            sharedData = value; // Ghi giá trị mới vào biến dùng chung
            System.out.println(threadName + " đã ghi xong."); // Thông báo ghi xong
        } catch (InterruptedException e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu bị gián đoạn
        } finally {
            writeLock.unlock(); // Mở khóa ghi sau khi ghi xong
        }
    }

    public static void main(String[] args) {
        ReadWriteExample example = new ReadWriteExample(); // Tạo đối tượng ví dụ

        // Định nghĩa tác vụ đọc cho các luồng đọc
        Runnable reader = () -> {
            for (int i = 0; i < 4; i++) { // Mỗi luồng đọc 3 lần
                example.readData(Thread.currentThread().getName());
            }
        };

        // Định nghĩa tác vụ ghi cho luồng ghi
        Runnable writer = () -> {
            for (int i = 0; i < 5; i++) { // Luồng ghi 2 lần
                example.writeData(Thread.currentThread().getName(), i * 100);
            }
        };

        // Tạo các luồng đọc và ghi với tên riêng
        Thread t1 = new Thread(reader, "Độc giả 1");
        Thread t2 = new Thread(reader, "Độc giả 2");
        Thread t3 = new Thread(writer, "Người ghi");

        t1.start(); // Khởi động luồng đọc 1
        t2.start(); // Khởi động luồng đọc 2
        t3.start(); // Khởi động luồng ghi
    }
}
