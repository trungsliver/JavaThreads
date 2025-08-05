package sequential;

public class SequentialExecutionDemo {
    public static void main(String[] args) {
        demo1();
        demo2();
    }

    private static void demo1() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Demo 1 - Step " + i);
        }
    }

    private static void demo2() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Demo 2 - Step " + i);
        }
    }
}
