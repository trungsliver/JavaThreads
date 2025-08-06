package concurrentCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronisedCollection {
    public static void main(String[] args) throws InterruptedException {

//        List<Integer> list = new ArrayList<>();

        // sử dụng synchronized để đảm bảo an toàn khi truy cập vào danh sách
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("List size: " + list.size());
    }

}
