package forkJoin.recursiveAction;

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

// Ý nghĩa: Ví dụ về cách sử dụng RecursiveAction trong ForkJoinPool để in các phần tử của mảng

// Mục đích: Hiển thị cách chia nhỏ công việc in mảng thành các tác vụ nhỏ hơn, và thực hiện chúng song song để tăng hiệu suất.

// Tình huống: Giả lập một hệ thống in các phần tử của mảng

public class PrintTask extends RecursiveAction {
    private int[] arr;
    private int start, end;
    // Ngưỡng để chia nhỏ công việc
    private static final int THRESHOLD = 3;

    public PrintTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        // Nếu số lượng phần tử trong khoảng nhỏ hơn hoặc bằng ngưỡng, in trực tiếp
        if (end - start <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println("Thread " + Thread.currentThread().getName() + " printing: " + arr[i]);
            }
        } else { // Nếu số lượng phần tử lớn hơn ngưỡng, chia nhỏ công việc
            // Tính chỉ số giữa để chia mảng thành hai phần
            int mid = (start + end) / 2;
            // Tạo hai tác vụ con để in cho hai phần của mảng
            PrintTask left = new PrintTask(arr, start, mid);
            PrintTask right = new PrintTask(arr, mid, end);
            // Gọi phương thức fork() để tính tác vụ bên trái song song
            left.fork();
            // Tính tác vụ bên phải trực tiếp
            right.compute();
            // Chờ tác vụ bên trái tính xong
            left.join();
        }
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new PrintTask(data, 0, data.length));
    }
}

