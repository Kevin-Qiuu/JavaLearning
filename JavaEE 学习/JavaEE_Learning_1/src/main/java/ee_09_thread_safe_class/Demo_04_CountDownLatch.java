package ee_09_thread_safe_class;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Demo_04_CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalI); // 线程休眠模拟处理正常业务
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "已完成任务。");
                count.countDown(); // 每当一个线程完成任务就计数减一
            });
            thread.start();
        }
        System.out.println("主线程开始等待所有线程任务执行....");
        count.await();
        System.out.println("线程全部执行完毕，可继续后续业务逻辑...");
    }
}


