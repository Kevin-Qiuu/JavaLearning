package ee_07_reentrantlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Demo_01_try_lock {
    public static int ret = 0;
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(); // 非公平锁
        Thread thread_1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                try{
                    lock.lock();
                    ++ret;
                    if (i == 2000)
                        throw new Exception("thread_1: i has got to 2000");
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
