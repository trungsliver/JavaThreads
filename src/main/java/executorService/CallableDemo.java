package executorService;

import java.util.concurrent.*;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)){
            // Future là một interface trong Java, đại diện cho kết quả của một tác vụ bất đồng bộ.
            // Nó cho phép bạn lấy kết quả trả về sau khi tác vụ hoàn thành hoặc kiểm tra trạng thái của tác vụ.
            // Dùng submit thay execute để nhận giá trị trả về từ Callable.
            Future<String> result = executorService.submit(new ReturnValueTask());

            // Chờ đợi tác vụ hoàn thành và lấy kết quả trả về
//            System.out.println(result.get());

//            // cancel() sẽ hủy tác vụ nếu nó chưa hoàn thành.
//            result.cancel(true);
//            // isCancelled() kiểm tra xem tác vụ đã bị hủy hay chưa.
//            boolean cancelled = result.isCancelled();
//            // isDone() kiểm tra xem tác vụ đã hoàn thành hay chưa.
//            boolean done = result.isDone();


            // Nếu tác vụ không hoàn thành trong thời gian chờ, sẽ ném ra TimeoutException
            System.out.println(result.get(6, TimeUnit.SECONDS));

            System.out.println("Main thread execution completed.");
        }
    }
}

// Callable là một interface trong Java, cho phép bạn định nghĩa một tác vụ có thể trả về giá trị sau khi thực hiện.
// Nó thường được sử dụng với ExecutorService để thực hiện các tác vụ bất đồng bộ và nhận kết quả trả về (thay cho Runnable).
class ReturnValueTask implements Callable<String>{
    @Override
    public String call() throws Exception {
        // Giả lập một tác vụ mất thời gian
        Thread.sleep(5000);

        return "Return String successfully!";
    }
}
