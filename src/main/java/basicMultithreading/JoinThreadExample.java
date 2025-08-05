package basicMultithreading;

public class JoinThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Thread 1 - Step " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                System.out.println("Thread 2 - Step " + i);
            }
        });

        // Bắt đầu các luồng
        System.out.println("Before executing the threads");
        t1.start();
        t2.start();

        // Phương thức join() sẽ chờ cho luồng t1 hoàn thành trước khi tiếp tục
//        t1.join();
        t2.join();
        System.out.println("Done executing the threads");
    }
}
