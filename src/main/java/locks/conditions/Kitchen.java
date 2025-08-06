package locks.conditions;

import java.util.concurrent.locks.*;

public class Kitchen {
    private String dish = null;
    private final Lock lock = new ReentrantLock();
    private final Condition dishAvailable = lock.newCondition();
    private final Condition dishTaken = lock.newCondition();

    // Đầu bếp: làm món ăn
    public void produce(String newDish) throws InterruptedException {
        lock.lock();
        try {
            while (dish != null) {
                System.out.println("Bếp đầy, đầu bếp chờ...");
                dishTaken.await();  // chờ cho đến khi món được lấy đi
            }
            dish = newDish;
            System.out.println("Đầu bếp nấu xong món: " + dish);
            dishAvailable.signal();  // thông báo cho phục vụ
        } finally {
            lock.unlock();
        }
    }

    // Phục vụ: lấy món ăn
    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (dish == null) {
                System.out.println("Bếp trống, phục vụ chờ...");
                dishAvailable.await();  // chờ món xuất hiện
            }
            System.out.println("Phục vụ lấy món: " + dish);
            dish = null;
            dishTaken.signal();  // thông báo cho đầu bếp tiếp tục nấu
        } finally {
            lock.unlock();
        }
    }

    // Hàm main để chạy ví dụ
    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();

        // Thread đầu bếp
        Thread chef = new Thread(() -> {
            String[] menu = {"Phở", "Bún bò", "Cơm gà", "Mì xào"};
            for (String dish : menu) {
                try {
                    kitchen.produce(dish);
                    Thread.sleep(1000); // Giả lập thời gian nấu
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread phục vụ
        Thread waiter = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    kitchen.consume();
                    Thread.sleep(1500); // Giả lập thời gian phục vụ
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Bắt đầu cả hai luồng
        chef.start();
        waiter.start();

    }
}

