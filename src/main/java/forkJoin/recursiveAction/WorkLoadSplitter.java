package forkJoin.recursiveAction;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// Mục đích: Hiển thị cách chia nhỏ công việc thành các tác vụ nhỏ hơn, và thực hiện chúng song song để tăng hiệu suất.
// Tình huống: Giả lập một hệ thống chia nhỏ công việc thành các tác vụ nhỏ hơn dựa trên khối lượng công việc.

public class WorkLoadSplitter extends RecursiveAction {
    // Khối lượng công việc được chia nhỏ
    private final long workLoad;

    public WorkLoadSplitter(long workLoad) {
        this.workLoad = workLoad;
    }

    // Phương thức compute() sẽ được gọi để thực hiện công việc chia nhỏ
    @Override
    protected void compute() {
        // Nếu khối lượng công việc lớn hơn 16, chia nhỏ công việc thành hai phần
        if (this.workLoad > 16) {
            System.out.println("Work Load too big, thus splitting : " + this.workLoad);
            // Chia khối lượng công việc thành hai phần bằng nhau
            long firstWorkLoad = this.workLoad/2;
            long secondWorkLoad = this.workLoad - firstWorkLoad;

            // Tạo hai tác vụ con để xử lý hai phần của khối lượng công việc
            WorkLoadSplitter firstSplit = new WorkLoadSplitter(firstWorkLoad);
            WorkLoadSplitter secondSplit = new WorkLoadSplitter(secondWorkLoad);

            // Gọi phương thức fork() để tính tác vụ bên trái song song
            firstSplit.fork();
            // Tính tác vụ bên phải trực tiếp
            secondSplit.fork();
        } else {
            System.out.println("Work Load within limits! Task being executed for workload : " + this.workLoad);
        }
    }
}

class WorkLoadSplitDemo {
    public static void main(String[] args) {
        try (ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors())) {
            WorkLoadSplitter splitter = new WorkLoadSplitter(128);
            // Gọi phương thức invoke() để thực hiện công việc chia nhỏ
            // Phương thức này sẽ chờ cho đến khi tất cả các tác vụ con hoàn thành và trả về kết quả cuối cùng
            pool.invoke(splitter);
        }
    }
}