package forkJoin.recursiveTask;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Ý nghĩa: Ví dụ về cách sử dụng RecursiveTask trong ForkJoinPool để tìm số lần xuất hiện của một phần tử trong mảng

// Mục đích: Hiển thị cách chia nhỏ công việc tìm kiếm số lần xuất hiện thành các tác vụ nhỏ hơn,
            // và thực hiện chúng song song để tăng hiệu suất.

// Tình huống: Giả lập một hệ thống tìm kiếm số lần xuất hiện của một phần tử trong mảng
            // bằng cách chia nhỏ công việc thành các tác vụ nhỏ hơn.

public class SearchOccurrenceTask extends RecursiveTask<Integer> {
    int[] arr;
    int start;
    int end;
    int searchElement;

    public SearchOccurrenceTask(int[] arr, int start, int end, int searchElement) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        this.searchElement = searchElement;
    }

    @Override
    protected Integer compute() {
        // Nếu khoảng tìm kiếm quá nhỏ, thực hiện tìm kiếm trực tiếp
        int size = end-start+1;
        // Ngưỡng để chia nhỏ công việc
        if(size > 50) {
            int mid = (start+end)/2;
            // Chia mảng thành hai phần và tạo hai tác vụ con để tìm kiếm số lần xuất hiện của phần tử
            SearchOccurrenceTask task1 = new SearchOccurrenceTask(arr, start, mid, searchElement);
            SearchOccurrenceTask task2 = new SearchOccurrenceTask(arr, mid+1, end, searchElement);
            // Gọi phương thức fork() để tính tác vụ bên trái song song
            task1.fork();
            task2.fork();

            return task1.join() + task2.join();
        } else {
            // Nếu khoảng tìm kiếm nhỏ hơn hoặc bằng ngưỡng, thực hiện tìm kiếm trực tiếp
            return search();
        }
    }

    // Phương thức tìm kiếm số lần xuất hiện của phần tử trong khoảng từ start đến end
    private Integer search() {
        int count = 0;
        for (int i = start; i <= end ; i++) {
            if (arr[i] == searchElement) {
                count++;
            }
        }
        return count;
    }
}

class FJPDemo {
    public static void main(String[] args) {
        int[] arr = new int[100];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(10) + 1;
        }

        int searchElement = random.nextInt(10) + 1;

        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            SearchOccurrenceTask task = new SearchOccurrenceTask(arr, 0, arr.length-1, searchElement);
            // Gọi phương thức invoke() để thực hiện công việc tìm kiếm
            Integer occurrence = pool.invoke(task);
            System.out.println("Array is : " + Arrays.toString(arr));
            System.out.printf("%d found %d times", searchElement, occurrence);
        }
    }
}