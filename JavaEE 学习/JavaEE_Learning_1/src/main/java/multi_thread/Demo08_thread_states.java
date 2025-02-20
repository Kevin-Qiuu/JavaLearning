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
    public static void main3(String[] args) throws InterruptedException {
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

    public static int count;
    public static final Object locker = new Object();
    // final修饰的引用变量不可以更改指向的对象，但对象是可以修改的
    // 锁对象的引用尽量不去变动，否则线程上的锁对象发生改变，导致不同线程的锁对象不同，synchronized 失去了其功能
    // 展现线程的 BLOCK 状态
    public static void main(String[] args) throws InterruptedException {
        Thread myThread_01 = new Thread(() -> {
            synchronized (locker){
                while (true)
                    count = 100;
            }
        }, "myThread_01");

        Thread myThread_02 = new Thread(() -> {
            synchronized (locker){
                while (true)
                    count = 99;
            }
        }, "myThread_02");
        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
        myThread_01.start();
        Thread.sleep(500);
        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
        myThread_02.start();
        System.out.println(myThread_01.getName() + " : " +myThread_01.getState());
        System.out.println(myThread_02.getName() + " : " +myThread_02.getState());
    }
}
