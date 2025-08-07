package otherConcepts.mutex;

import java.util.concurrent.Semaphore;

public class ATMExample {
    private static final Semaphore atmMutex = new Semaphore(1); // chỉ cho 1 người vào ATM

    public static void main(String[] args) {
        Runnable user = () -> {
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + " đang đợi đến lượt...");
                atmMutex.acquire();
                System.out.println(name + " đang rút tiền...");
                Thread.sleep(2000);  // giả lập thời gian xử lý
                System.out.println(name + " đã rút xong tiền.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                atmMutex.release();
            }
        };

        for (int i = 1; i <= 3; i++) {
            new Thread(user, "Khách " + i).start();
        }
    }
}

