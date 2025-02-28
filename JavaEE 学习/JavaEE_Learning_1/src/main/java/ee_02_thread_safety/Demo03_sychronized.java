package ee_02_thread_safety;

class CounterDemo10{
    public int count = 0;
    public synchronized void increase(){
        count++;
    }
}

public class Demo03_sychronized {
    static int count = 0;
    static final Object locker = new Object();
    static final Object locker_01 = new Object();
    public static void main1(String[] args) throws InterruptedException {
        CounterDemo10 counterDemo10 = new CounterDemo10();
        Thread myThread_01 = new Thread(() -> {
            for (int i = 0; i < 50_0000; i++) {
                synchronized (locker){
                    ++count;
                }
            }
        }, "myThread_01");
        Thread myThread_02 = new Thread(() -> {
            for (int i = 0; i < 50_0000; i++) {
                synchronized (locker){
                    ++count;
                }
            }
        }, "myThread_02");
        myThread_01.start();
        myThread_02.start();

        myThread_01.join();
        myThread_02.join();
        System.out.println("count = "+ count);
    }

    public static void main2(String[] args) throws InterruptedException {
        Thread myThread_01 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }
                synchronized (locker){
                    System.out.println("myThread_01 get count, count = " + (++count));
                }
            }
        }, "myThread_01");
        Thread myThread_02 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }
                synchronized (locker){
                    System.out.println("myThread_02 get count, count = " + (--count));
                }
            }
        }, "myThread_02");
        myThread_01.start();
        myThread_02.start();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThread_01 = new Thread(() -> {
            for (int i = 0; i < 100_0000; i++) {
                synchronized (locker){
                    ++count;
                }
            }
        }, "myThread_01");

        Thread myThread_02 = new Thread(() -> {
            for (int i = 0; i < 100_0000; i++) {
                synchronized (locker_01){
                    // 使用的锁对象不一致，在抢占公共资源时根本用不到锁
                    --count;
                }
            }
        }, "myThread_02");

        myThread_01.start();
        myThread_02.start();
        // 很多次观察现象时都忘记了等待进程加载完再去查看！！
        myThread_01.join();
        myThread_02.join();
        System.out.println("count = " + count);
    }
}
