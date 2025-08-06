package concurrentCollection.countdownLatch;

import java.util.concurrent.CountDownLatch;

public class Restaurant {
    public static void main(String[] args) throws InterruptedException {
        int numberOfChefs = 3;
        // Tạo một CountDownLatch với số lượng là số lượng đầu bếp
        CountDownLatch latch = new CountDownLatch(numberOfChefs);

        // Tạo và khởi động các luồng đầu bếp
        new Thread(new Chef("Chef A", "Pasta", latch)).start();
        new Thread(new Chef("Chef B", "Pizza", latch)).start();
        new Thread(new Chef("Chef C", "Salad", latch)).start();

        // Chờ cho tất cả các đầu bếp hoàn thành công việc của họ
        latch.await();

        System.out.println("All dishes are ready! The restaurant can now serve customers.");
    }
}

class Chef implements Runnable {
    private String name;
    private String dish;
    private final CountDownLatch latch;

    protected Chef(String name, String dish, CountDownLatch latch) {
        this.name = name;
        this.dish = dish;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " is preparing " + dish);
            Thread.sleep(2000); // Simulate time taken to prepare the dish
            System.out.println(name + " has finished preparing " + dish);
            // dùng để đếm ngược latch
            latch.countDown(); // Decrement the latch count
        } catch (InterruptedException e) {
            throw  new RuntimeException(e);
        }
    }
}