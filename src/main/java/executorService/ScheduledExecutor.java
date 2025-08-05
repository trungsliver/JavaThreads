package executorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        // chạy một task định kỳ mỗi 2 giây, bắt đầu sau 1 giây
        service.scheduleAtFixedRate(new ProbeTask(), 1000, 2000, TimeUnit.MILLISECONDS);

        try {
            // Giữ cho chương trình chạy trong 10 giây để quan sát kết quả
            Thread.sleep(10000);
            // Dừng service sau khi hoàn thành
            service.shutdown();
        } catch (InterruptedException e){
            service.shutdownNow();
        }
    }
}

class ProbeTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Probing end point for updates ....");
    }
}
