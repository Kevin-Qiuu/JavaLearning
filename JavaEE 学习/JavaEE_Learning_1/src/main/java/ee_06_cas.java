import java.util.concurrent.atomic.AtomicInteger;

public class ee_06_cas {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(0);  // 定义一个原子类
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                atomicInteger.getAndIncrement();
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                atomicInteger.getAndIncrement();
            }
        }, "thread2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(atomicInteger.get());
    }
}
