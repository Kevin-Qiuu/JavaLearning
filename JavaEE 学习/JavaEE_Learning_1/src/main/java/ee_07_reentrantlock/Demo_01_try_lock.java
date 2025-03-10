package ee_07_reentrantlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_01_try_lock {
    public static int ret = 0;

    public static void main(String[] args) throws InterruptedException {
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
        System.out.println("Thread_1:"+thread_1.getState());
        System.out.println("Thread_2:"+thread_2.getState());
        lock.lock();
        condition_1.signalAll(); // 唤醒阻塞的全部线程
        lock.unlock();


    }
    public static void main1(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(); // 非公平锁
        Thread thread_1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                try{
                    lock.lock();
                    ++ret;
                    if (i == 2000) {
//                        throw new Exception("thread_1: i has got to 2000");
                        lock.lock();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                lock.unlock(); // 如果前面的代码抛出了异常，不能够顺利释放锁，后面的线程无法获取锁，因而出现死锁

            }
        });
        Thread thread_2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                try{
                    lock.lock();
                    ++ret;
//                    if (i == 2500)
//                        throw new Exception("thread_2: i has got to 2500");
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
