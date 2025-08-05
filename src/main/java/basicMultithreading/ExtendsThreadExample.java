package basicMultithreading;

public class ExtendsThreadExample {
    public static void main(String[] args) {
        Thread t1 = new Thread1();
        Thread t2 = new Thread2();

        // Phương thức start() sẽ gọi phương thức run() trong mỗi luồng
        t1.start();
        t2.start();
    }
}

class Thread1 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Thread 1 - Step " + i);
        }
    }
}

class Thread2 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Thread 2 - Step " + i);
        }
    }
}