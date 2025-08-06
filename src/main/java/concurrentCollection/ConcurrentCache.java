package concurrentCollection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCache {
    // cache lưu trữ các cặp key - value
    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            // Mỗi luồng sẽ sử dụng một key duy nhất
            final int threadNum = i;
            // Tạo các luồng để thêm phần tử vào cache
            new Thread(() -> {
                String key = "Key @ " + threadNum;
                for (int j = 0; j < 3; j++) {
                    String value = getCachedValue(key);
                    System.out.println("Thread " + Thread.currentThread().getName() + " key: " + key + " value: " + value);
                }
            }).start();
        }
    }

    // Phương thức để lấy giá trị từ cache hoặc tính toán nếu không có trong cache
    private static String getCachedValue( final String key) {
        String value = cache.get(key);
        // Nếu giá trị không có trong cache, tính toán giá trị mới
        if (value == null) {
            // Giả lập việc tính toán giá trị
            value = compute(key);
            // Thêm giá trị vào cache
            cache.put(key, value);
        }
        return value;
    }

    // Phương thức giả lập tính toán giá trị cho key
    private static String compute(final String key) {
        System.out.println(key + " not found in cache, computing value...");
        // Giả lập thời gian tính toán
        try {
            Thread.sleep(100); // Giả lập thời gian tính toán
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return  "value of " + key;
    }
}
