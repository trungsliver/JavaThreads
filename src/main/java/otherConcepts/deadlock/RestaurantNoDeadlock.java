package otherConcepts.deadlock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class RestaurantNoDeadlock {

    // Tạo 2 khóa đại diện cho dao và thớt
    static final ReentrantLock knife = new ReentrantLock();
    static final ReentrantLock cuttingBoard = new ReentrantLock();

    public static void main(String[] args) {

        // Đầu bếp 1
        Thread chef1 = new Thread(() -> {
            while (true) {
                try {
                    if (knife.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("👨‍🍳 Chef 1 lấy được dao.");
                            if (cuttingBoard.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("👨‍🍳 Chef 1 lấy được thớt và đang nấu ăn...");
                                    break; // Thành công, thoát khỏi vòng lặp
                                } finally {
                                    cuttingBoard.unlock();
                                }
                            } else {
                                System.out.println("❌ Chef 1 không lấy được thớt, nhả dao...");
                            }
                        } finally {
                            knife.unlock();
                        }
                    } else {
                        System.out.println("❌ Chef 1 không lấy được dao, thử lại...");
                    }
                    Thread.sleep(50); // Nghỉ chút rồi thử lại
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Đầu bếp 2
        Thread chef2 = new Thread(() -> {
            while (true) {
                try {
                    if (cuttingBoard.tryLock(100, TimeUnit.MILLISECONDS)) {
                        try {
                            System.out.println("👨‍🍳 Chef 2 lấy được thớt.");
                            if (knife.tryLock(100, TimeUnit.MILLISECONDS)) {
                                try {
                                    System.out.println("👨‍🍳 Chef 2 lấy được dao và đang nấu ăn...");
                                    break; // Thành công, thoát khỏi vòng lặp
                                } finally {
                                    knife.unlock();
                                }
                            } else {
                                System.out.println("❌ Chef 2 không lấy được dao, nhả thớt...");
                            }
                        } finally {
                            cuttingBoard.unlock();
                        }
                    } else {
                        System.out.println("❌ Chef 2 không lấy được thớt, thử lại...");
                    }
                    Thread.sleep(50); // Nghỉ chút rồi thử lại
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        chef1.start();
        chef2.start();
    }
}
