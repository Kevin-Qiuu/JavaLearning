package ee_06_cas;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo_01_atomic {
    public static void main(String[] args) {
        // 实例一个原子 Integer 对象，构造函数传递一个起始值。
        AtomicInteger atomicInteger = new AtomicInteger(0);
        atomicInteger.incrementAndGet();
    }

    public static void main3(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }

    public static void main2(String[] args) {
        // AtomicInteger 的自增自减的方法
        AtomicInteger count = new AtomicInteger(0);
        int ret = count.incrementAndGet();  // ++count
        System.out.println("count: " + count);
        System.out.println("ret: " + ret);
        ret = count.getAndIncrement(); // count++
        System.out.println("count: " + count);
        System.out.println("ret: " + ret);
        ret = count.decrementAndGet(); // --count;
        System.out.println("count: " + count);
        System.out.println("ret: " + ret);
        ret = count.getAndDecrement(); // count--
        System.out.println("count: " + count);
        System.out.println("ret: " + ret);

    }

    public static void main1(String[] args) throws InterruptedException {
        // 基于自旋锁的原子类，这个是一个整型原子类
        AtomicInteger count = new AtomicInteger(0);
        Thread thread_01 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                count.incrementAndGet();
            }
        });
        Thread thread_02 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                count.incrementAndGet();
            }
        });

        thread_01.start();
        thread_02.start();

        thread_01.join();
        thread_02.join();


        System.out.println(count.get());

    }

}
