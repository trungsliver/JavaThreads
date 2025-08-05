package basicMultithreading;

public class ThreadPriorityExample {
    public static void main(String[] args) {
//        System.out.println("Default Thread Priority");
//        System.out.println("Name: " + Thread.currentThread().getName());
//        System.out.println("Priority: " + Thread.currentThread().getPriority());
//
//        System.out.println("\nChanging Thread Priority to MAX_PRIORITY");
//        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
//        System.out.println("Name: " + Thread.currentThread().getName());
//        System.out.println("Priority: " + Thread.currentThread().getPriority());
//
//        System.out.println("\nChanging Thread Priority to MIN_PRIORITY");
//        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
//        System.out.println("Name: " + Thread.currentThread().getName());
//        System.out.println("Priority: " + Thread.currentThread().getPriority());

        // This is the main thread
        System.out.println(Thread.currentThread().getName() + " say Hi!");

        // Create two threads with different priorities

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " say Hi as well!");
        });

        t1.setPriority(Thread.MIN_PRIORITY);


        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " say Hi as well!");
        });

        t2.setPriority(Thread.MAX_PRIORITY);

        // Run the threads
        t1.start();
        t2.start();
    }
}
