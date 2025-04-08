package ee_02_thread_safety;

class TestDemo {
    public int flag;
}

public class Demo06_1_wait {
    static volatile int flag = 0;
    public static Object locker = new Object();

    // 验证当多个线程同时阻塞时，在被唤醒后产生锁竞争
    // 竞争到锁后，是接着自己逻辑的阻塞处代码运行还是在synchronized代码块开头运行？
    // 理论上：是接着自己逻辑的阻塞处代码运行
    public static void main(String[] args) throws InterruptedException {
        Thread thread_1 = new Thread(() -> {
            synchronized (locker) {
                System.out.println(Thread.currentThread().getName() + " get locker ...");

                while (flag == 0) {
                    System.out.println(Thread.currentThread().getName() + " will be waiting ...");
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 验证 synchronized 代码块原子性
                // 唤醒抢到锁后，只会有一个线程抢到锁，同时一并执行下面的所有语句
                if (flag != 0) {
                    System.out.println(Thread.currentThread().getName() + " get locker again ...");
                    System.out.println(Thread.currentThread().getName() + " has been waken up !!!");
                }

            }
        }, "thread_1");

        Thread thread_2 = new Thread(() -> {
            synchronized (locker) {
                System.out.println(Thread.currentThread().getName() + " get locker ...");

                while (flag == 0) {
                    System.out.println(Thread.currentThread().getName() + " will be waiting ...");
                    try {
                        locker.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (flag != 0) {
                    System.out.println(Thread.currentThread().getName() + " get locker again ...");
                    System.out.println(Thread.currentThread().getName() + " has been waken up !!!");
                }

            }
        }, "thread_2");

        Thread thread_3 = new Thread(() -> {

            // 唤醒他们
            synchronized (locker) {
                System.out.println(Thread.currentThread().getName() + " get locker ...");
                flag = 1;
                System.out.println(Thread.currentThread().getName() + " will notify all ...");
                locker.notifyAll();
            }

        }, "thread_3");

        thread_1.start();
        thread_2.start();
        // 等待 1 s
        Thread.sleep(1000);
        thread_3.start();
    }
}
