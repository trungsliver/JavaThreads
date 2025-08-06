package concurrentCollection.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class MultiStageTour {
    // Số lượng khách du lịch và số giai đoạn của tour
    private static final int NUM_TOURISTS = 5;
    private static final int NUM_STAGES = 3;

    // CyclicBarrier để đồng bộ hóa các khách du lịch
    private static final CyclicBarrier barrier = new CyclicBarrier(NUM_TOURISTS,() -> {
        System.out.println("Tour guide starts speaking...");
    });


    public static void main(String[] args) {
        // Mỗi khách du lịch sẽ đi qua các giai đoạn của tour
        for (int i = 0; i < NUM_TOURISTS; i++) {
            Thread touristThread = new Thread(new Tourist(i));
            touristThread.start();
        }
    }

    // Lớp đại diện cho khách du lịch
    static class Tourist implements Runnable {
        private final int touristId;

        public Tourist(int touristId) {
            this.touristId = touristId;
        }

        @Override
        public void run() {
            // Mỗi khách du lịch sẽ đi qua các giai đoạn của tour
            for (int i = 0; i < NUM_STAGES; i++) {
                // Perform actions at each stage
                // Giả lập thời gian di chuyển đến giai đoạn tiếp theo
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Tourist " + touristId + " arrives at Stage " + (i + 1));

                // Wait for all tourists to arrive at the current stage
                // Đợi các khách du lịch khác đến giai đoạn hiện tại
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
