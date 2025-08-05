package threadSynchronisation;

public class SynchronizationDemo {

    // This is a shared resource
    private  static  int counter = 0;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                // wrong way to increment the counter
//                counter++;
                // correct way to increment the counter (user synchronization)
                increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                // wrong way to increment the counter
//                counter++;
                // correct way to increment the counter (user synchronization)
                increment();
            }
        });

        // Start both threads
        t1.start();
        t2.start();

        // Wait for both threads to finish
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Print the final value of the counter
        System.out.println("Final counter value: " + counter);
    }

    // This method is synchronized to ensure that only one thread can increment the counter at a time
    // synchronized: chỉ được thực hiện bởi một thread tại một thời điểm
    private synchronized static void increment() {
        counter++;
    }
}
