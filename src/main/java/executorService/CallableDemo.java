package executorService;

import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)){
            // Future là một interface trong Java, đại diện cho kết quả của một tác vụ bất đồng bộ.
            // Nó cho phép bạn lấy kết quả trả về sau khi tác vụ hoàn thành hoặc kiểm tra trạng thái của tác vụ.
            // Dùng submit thay execute để nhận giá trị trả về từ Callable.
            Future<String> result = executorService.submit(new ReturnValueTask());

            System.out.println(result.get());
        }
    }
}

// Callable là một interface trong Java, cho phép bạn định nghĩa một tác vụ có thể trả về giá trị sau khi thực hiện.
// Nó thường được sử dụng với ExecutorService để thực hiện các tác vụ bất đồng bộ và nhận kết quả trả về (thay cho Runnable).
class ReturnValueTask implements Callable<String>{
    @Override
    public String call() throws Exception {
        return "Return String successfully!";
    }
}
