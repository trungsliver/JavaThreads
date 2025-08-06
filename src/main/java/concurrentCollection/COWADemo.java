package concurrentCollection; // Khai báo package chứa class này

import java.util.Arrays; // Import lớp Arrays để thao tác với mảng
import java.util.List; // Import interface List
import java.util.Random; // Import lớp Random để sinh số ngẫu nhiên
import java.util.concurrent.CopyOnWriteArrayList; // Import lớp CopyOnWriteArrayList cho collection thread-safe

public class COWADemo {
    public static void main(String[] args) {
        // Tạo đối tượng Simulation và chạy mô phỏng
        Simulation simulation = new Simulation();
        simulation.simulate();
    }
}

// Lớp mô phỏng hoạt động đọc/ghi trên danh sách thread-safe
class Simulation {
    private final List<Integer> list; // Danh sách số nguyên dùng CopyOnWriteArrayList

    public Simulation() {
        list = new CopyOnWriteArrayList<>(); // Khởi tạo danh sách thread-safe
        list.addAll(Arrays.asList(0,0,0,0,0,0,0,0)); // Thêm 8 số 0 vào danh sách
    }

    public void simulate() {
        // Tạo 3 luồng ghi và 1 luồng đọc
        Thread one = new Thread(new WriteTask(list));
        Thread two = new Thread(new WriteTask(list));
        Thread three = new Thread(new WriteTask(list));
        Thread four = new Thread(new ReadTask(list));

        // Khởi động các luồng
        one.start();
        two.start();
        three.start();
        four.start();
    }
}

// Nhiệm vụ đọc danh sách liên tục
class ReadTask implements Runnable {
    private final List<Integer> list;

    public ReadTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000); // Dừng 1 giây giữa mỗi lần đọc
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(list); // In ra danh sách hiện tại
        }
    }
}

// Nhiệm vụ ghi ngẫu nhiên vào danh sách liên tục
class WriteTask implements Runnable {
    private List<Integer> list;
    private Random random;

    public WriteTask(List<Integer> list) {
        this.list = list;
        this.random = new Random(); // Khởi tạo đối tượng sinh số ngẫu nhiên
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1200); // Dừng 1.2 giây giữa mỗi lần ghi
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Ghi giá trị ngẫu nhiên vào vị trí ngẫu nhiên trong danh sách
            list.set(random.nextInt(list.size()), random.nextInt(10));
        }
    }
}