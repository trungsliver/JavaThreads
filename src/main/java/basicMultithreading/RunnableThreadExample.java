package basicMultithreading;

public class RunnableThreadExample {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ThreadOne());
        Thread t2 = new Thread(new ThreadTwo());
        Thread t3 = new Thread(() -> {
            for (int i = 1; i <= 20; i++) {
                System.out.println("Thread 3 - Step " + i);
            }
        });

        // Phương thức start() sẽ gọi phương thức run() trong mỗi luồng
        t1.start();
        t2.start();
        t3.start();
    }
}

class ThreadOne implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Thread 1 - Step " + i);
        }
    }
}

class ThreadTwo implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Thread 2 - Step " + i);
        }
    }
}