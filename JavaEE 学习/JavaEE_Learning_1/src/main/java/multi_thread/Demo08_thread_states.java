package multi_thread;

public class Demo08_thread_states {
    // 展现线程的 NEW、 RUNNABLE 和 TERMINATED 状态
    public static void main1(String[] args) throws InterruptedException {
        Thread myThread = new Thread(()->{
            for (int i = 0; i < 10000; i++) {
                if (i % 2500 == 0)
                    System.out.println("Hello, my little thread!");
            }
        });
        System.out.println(myThread.getState());
        myThread.start();
        System.out.println(myThread.getState());
        myThread.join();
        System.out.println(myThread.getState());
    }

    // 展现线程的 WAITING 和 TIMED_WAITING 状态
    public static void main(String[] args) throws InterruptedException {
        Object locker = new Object();
        Thread myThread_01 = new Thread(()->{
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello, my little thread!");
            }
        }, "myThread_01");

        Thread myThread_02 = new Thread(()->{
            synchronized (locker) {
                try {
                    locker.wait();
                } catch (InterruptedException e) {
                    System.out.println("Interrupt thread: " + Thread.currentThread().getName());
                    Thread.currentThread().interrupt();
                }
                System.out.println("Hello, my little thread!");
            }
        }, "myThread_02");

        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
        myThread_01.start();
        myThread_02.start();
        Thread.sleep(500);
        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
        myThread_01.join();
        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
        System.out.println("waiting for 2 seconds...");
        Thread.sleep(2000);
        myThread_02.interrupt();
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
    }
}
