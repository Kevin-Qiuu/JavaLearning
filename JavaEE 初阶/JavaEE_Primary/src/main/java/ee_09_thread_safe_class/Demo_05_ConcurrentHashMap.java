package ee_09_thread_safe_class;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Demo_05_ConcurrentHashMap {
    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    hashMap.put(finalI + "__" + j, Thread.currentThread().getName()
                                + "__" + j);
                }
                try {
                    TimeUnit.SECONDS.sleep(2); // 休眠两秒模拟真实业务处理
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
            thread.start();
        }
        countDownLatch.await();
        System.out.println(hashMap);
    }
}
