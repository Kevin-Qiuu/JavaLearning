package ee_09_thread_safe_class;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


public class Demo_03_Semaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphore.acquire(); // acquire方法可能会使线程阻塞等待，所以需要捕获中断异常
                    System.out.println(Thread.currentThread().getName() + " 获取资源...");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + "释放资源...");
            });
            thread.start();
        }
    }
}
