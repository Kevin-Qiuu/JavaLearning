package multi_thread;

public class Demo09_thread_safety {
    public static int i;
    // 两个线程同时对一个变量进行自增操作
    public static void main(String[] args) throws InterruptedException {
        Thread myThread_01 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                ++i;
            }
        });
        Thread myThread_02 = new Thread(() -> {
            for (int j = 0; j < 5000; j++) {
                ++i;
            }
        });
        myThread_01.start();
        myThread_02.start();
        myThread_01.join();
        myThread_02.join();
        System.out.println(i);
    }

}
