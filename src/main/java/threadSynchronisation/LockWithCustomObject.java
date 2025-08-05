package threadSynchronisation;

public class LockWithCustomObject {
    private static int counter1 = 0;
    private static int counter2 = 0;

    // Using custom lock objects for synchronization - sử dung các đối tượng khóa tùy chỉnh để đồng bộ hóa
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment1();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                increment2();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final counter1 value: " + counter1);
        System.out.println("Final counter2 value: " + counter2);
    }


    private static void increment1() {
        synchronized (lock1) {
            counter1++;
        }
    }

    private static void increment2() {
        synchronized (lock1) {
            counter2++;
        }
    }
}
