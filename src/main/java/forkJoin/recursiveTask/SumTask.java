package forkJoin.recursiveTask;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

// Ý nghĩa: Ví dụ về cách sử dụng RecursiveTask trong ForkJoinPool để tính tổng các phần tử của mảng

// Mục đích: Hiển thị cách chia nhỏ công việc tính tổng thành các tác vụ nhỏ hơn, và thực hiện chúng song song để tăng hiệu suất.

// Tình huống: Giả lập một hệ thống tính tổng các phần tử của mảng bằng cách chia nhỏ công việc thành các tác vụ nhỏ hơn.

public class SumTask extends RecursiveTask<Integer> {
    private int[] arr;
    private int start, end;
    // Ngưỡng để chia nhỏ công việc
    private static final int THRESHOLD = 3;

    public SumTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    // Phương thức compute() sẽ được gọi để thực hiện công việc tính tổng
    @Override
    protected Integer compute() {
        // Nếu số lượng phần tử trong khoảng nhỏ hơn hoặc bằng ngưỡng, tính tổng trực tiếp
        if (end - start <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        } else { // Nếu số lượng phần tử lớn hơn ngưỡng, chia nhỏ công việc
            // Tính chỉ số giữa để chia mảng thành hai phần
            int mid = (start + end) / 2;
            // Tạo hai tác vụ con để tính tổng cho hai phần của mảng
            // Bên trái sẽ được tính song song, bên phải sẽ được tính trực tiếp
            SumTask left = new SumTask(arr, start, mid);
            SumTask right = new SumTask(arr, mid, end);
            // Gọi phương thức fork() để tính tác vụ bên trái song song
            // và phương thức compute() để tính tác vụ bên phải trực tiếp
            left.fork();                        // tính bên trái song song
            int rightResult = right.compute();  // tính bên phải trực tiếp
            int leftResult = left.join();       // chờ bên trái tính xong
            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8};
        ForkJoinPool pool = new ForkJoinPool();
        int total = pool.invoke(new SumTask(data, 0, data.length));
        System.out.println("Tổng: " + total);
    }
}
