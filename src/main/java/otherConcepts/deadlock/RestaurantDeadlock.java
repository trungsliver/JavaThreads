package otherConcepts.deadlock;

public class RestaurantDeadlock {

    // Tài nguyên dùng chung
    static final Object knife = new Object(); // Dao
    static final Object cuttingBoard = new Object(); // Thớt

    public static void main(String[] args) {

        // Đầu bếp 1: Lấy dao trước, sau đó mới lấy thớt
        Thread chef1 = new Thread(() -> {
            synchronized (knife) {
                System.out.println("👨‍🍳 Chef 1 đã lấy dao.");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                System.out.println("👨‍🍳 Chef 1 đang chờ thớt...");
                synchronized (cuttingBoard) {
                    System.out.println("👨‍🍳 Chef 1 đã có cả dao và thớt để nấu ăn.");
                }
            }
        });

        // Đầu bếp 2: Lấy thớt trước, sau đó mới lấy dao
        Thread chef2 = new Thread(() -> {
            synchronized (cuttingBoard) {
                System.out.println("👨‍🍳 Chef 2 đã lấy thớt.");

                try { Thread.sleep(100); } catch (InterruptedException e) {}

                System.out.println("👨‍🍳 Chef 2 đang chờ dao...");
                synchronized (knife) {
                    System.out.println("👨‍🍳 Chef 2 đã có cả dao và thớt để nấu ăn.");
                }
            }
        });

        // Bắt đầu 2 đầu bếp
        chef1.start();
        chef2.start();
    }
}

