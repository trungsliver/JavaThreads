package basicMultithreading;

public class DeamonUserThreadDemo {
    public static void main(String[] args) {
        Thread backgroundThread = new Thread(new DeamonHelper());
        Thread userThread = new Thread(new UserHelper());

        // Thiết lập backgroundThread là luồng daemon
        backgroundThread.setDaemon(false);

        backgroundThread.start();
        userThread.start();
    }
}

class DeamonHelper implements Runnable{
    @Override
    public void run() {
        int count = 0;
        while (count < 500){
            try {
                Thread.sleep(1000); // Giả lập công việc
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            count++;
            System.out.println("Deamon helper is running ...");
        }
    }
}

class  UserHelper implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User helper done with excution !!!");
    }
}