package otherConcepts.volatileKeyword;

//Tình huống:
    //Bạn đang lập trình một hệ thống điều khiển đèn giao thông tự động, trong đó có:
    //Thread A: một cảm biến thời gian thực liên tục kiểm tra điều kiện giao thông và khi cần thì đặt cờ stopSignal = true
            // để yêu cầu tắt hệ thống đèn giao thông (ví dụ trong trường hợp khẩn cấp).
    //Thread B: một luồng chính điều khiển đèn, đang chạy trong vòng lặp và kiểm tra biến stopSignal.

//Khi khai báo stopSignal là volatile, mọi thread sẽ:
    //Ghi giá trị mới trực tiếp vào RAM
    //Đọc giá trị từ RAM, không đọc từ cache

public class TrafficLightSystem {

    // Không được khai báo volatile: các luồng khác nhau không nhìn thấy giá trị mới nhất => hệ thống không dừng lại khi cần thiết.
//    static boolean stopSignal = false;

    // Biến stopSignal được khai báo là volatile để đảm bảo rằng mọi luồng đều nhìn thấy giá trị mới nhất
    static volatile boolean stopSignal = false;


    public static void main(String[] args) {
        Thread controller = new Thread(() -> {
            while (!stopSignal) {
                // đang điều khiển đèn giao thông
            }
            System.out.println("Hệ thống dừng lại!");
        });

        Thread emergencySensor = new Thread(() -> {
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            stopSignal = true;  // ra tín hiệu dừng
        });

        controller.start();
        emergencySensor.start();
    }
}

