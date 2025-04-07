package ee_07_reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo_01_ReentrantLock {
    public static int ret = 0;
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void increase_write_lock(){
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        ++ret;
        writeLock.unlock();
    }

    public static void increase_read_lock(){
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        readLock.lock();
        ++ret;
        readLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread_1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
//                increase_read_lock();
                increase_write_lock();
            }

        });

        Thread thread_2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
//                increase_read_lock();
                increase_write_lock();

            }
        });

        thread_1.start();
        thread_2.start();

        thread_1.join();
        thread_2.join();

        System.out.println(ret);
    }

    public static void main4(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("Thread_1 is calling...");
                Thread.sleep(2000);
                // lock.tryLock()
                // 调用这个方法的线程如果在规定时间没有抢到锁返回 false，否则返回 true
                // 这个方法规定了线程阻塞等待锁的时间，不会出现一直死等的情况
                if (lock.tryLock(3, TimeUnit.SECONDS)) {
                    try {
                        System.out.println("Thread_1 got lock...");
                    } finally {
                        lock.unlock(); // 只有抢到锁后才可以执行 unlock，否则没有锁还调用 unlock 会异常
                    }
                } else {
                    System.out.println("Thread_1 cannot get lock...");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread_2 is calling...");
            try {
//                TimeUnit.SECONDS.sleep(2);
                System.out.println("Thread_2 got the lock and will be sleeping....");
                lock.lock();
                TimeUnit.SECONDS.sleep(8);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Thread_2 will release the lock...");
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static void main3(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock(true); // 传递 true 为公平锁
        ReentrantLock lock = new ReentrantLock();
        // 创建两个条件锁
        Condition condition_1 = lock.newCondition();

        Thread thread_1 = new Thread(() -> {
            lock.lock();
            try {
                int cond = 0;
                while (true) {
                    if (cond == 4) {
                        System.out.println("线程 1 已从 condition_1 中解除，即将结束。。。");
                        return;
                    }
                    Thread.sleep(2000);
                    System.out.println("Thread_1 is calling...");
                    ++cond;
                    if (cond == 3) {
                        System.out.println("线程 1 满足了 condition_1，即将上锁...");
                        condition_1.await(); // 进入 condition_1 的阻塞队列
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });

        Thread thread_2 = new Thread(() -> {
            lock.lock();
            try {
                int cond = 0;
                while (true) {
                    if (cond == 5) {
                        System.out.println("线程 2 已从 condition_1 中解除，即将结束。。。");
                        return;
                    }
                    Thread.sleep(2000);
                    System.out.println("Thread_2 is calling...");
                    ++cond;
                    if (cond == 5) {
                        System.out.println("线程 2 满足了 condition_1，即将上锁...");
                        condition_1.await(); // 进入 condition_1 的阻塞队列
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        });


        thread_1.start();
        thread_2.start();


        Thread.sleep(20000);
        System.out.println("主线程即将唤醒全部线程。。。");
        System.out.println("Thread_1:" + thread_1.getState());
        System.out.println("Thread_2:" + thread_2.getState());
        lock.lock();
        condition_1.signalAll(); // 唤醒阻塞的全部线程
        lock.unlock();


    }

    public static void main1(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(); // 非公平锁
        Thread thread_1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                try {
                    lock.lock();
                    ++ret;
                    if (i == 2000) {
                        lock.lock();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                    lock.unlock();
                }

            }
        });
        Thread thread_2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                try {
                    lock.lock();
                    ++ret;
                    if (i == 2500)
                        throw new Exception("thread_2: i has got to 2500");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }

            }
        }, "thread_2");

        thread_1.start();
        thread_2.start();

        thread_1.join();
        thread_2.join();

        System.out.println(ret);

    }
}
