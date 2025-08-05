package threadSynchronisation;

public class WaitAndNotifyDemo {

    // Khóa đồng bộ hóa dùng chung
    private static Object LOCK = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                one();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                two();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Bắt đầu cả hai luồng
        t1.start();
        t2.start();
    }

    private  static void one() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method one");
            LOCK.wait(); // Thread 1 sẽ đợi cho đến khi được thông báo
            System.out.println("Back again in the method one");
        }
    }

    private static void two() throws InterruptedException {
        synchronized (LOCK) {
            System.out.println("Hello from method two");
            LOCK.notify(); // Thông báo cho một luồng đang đợi
            System.out.println("Hello from method two after notify");
        }
    }
}
