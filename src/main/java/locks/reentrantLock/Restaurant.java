package locks.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private final ReentrantLock lock = new ReentrantLock();
    private String dish = null;

    public void cook(String food) {
        lock.lock(); // chiếm khóa
        try {
            System.out.println("Đầu bếp nấu món: " + food);
            dish = food;

            // Gọi lại hàm check món ăn (vẫn giữ lock, reentrant!)
            checkDish();
        } finally {
            lock.unlock(); // nhả khóa
        }
    }

    public void checkDish() {
        lock.lock(); // Không bị chặn vì đã giữ lock
        try {
            System.out.println("Kiểm tra lại món: " + dish);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Restaurant r = new Restaurant();
        r.cook("Phở bò");
    }
}
