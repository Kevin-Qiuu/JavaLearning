package ee_04_block_queue;

import java.util.concurrent.BlockingQueue;

public class Demo01_BlockQueue {
    public static void main(String[] args) throws InterruptedException {
        // 再实现一遍消费者生产者的案例吧
        // 使用 while 判断是否已满
        // API：take（拿取）
        // API：put （放入）
        // 满的时候使放入消息队列的线程阻塞
        // 空的时候使拿取消息队列的线程阻塞

        MyBlockQueue myBlockQueue = new MyBlockQueue(5);
        Thread producer_1 = new Thread(() -> {
            while (true) {
                try {
                    myBlockQueue.put(1);
                    System.out.println(Thread.currentThread().getName() + " 放入了 " + 1);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "producer_1");

        Thread producer_2 = new Thread(() -> {
            while (true){
                try {
                    myBlockQueue.put(2);
                    System.out.println(Thread.currentThread().getName() + " 放入了 " + 2);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "producer_2");

        Thread producer_3 = new Thread(() -> {
            while (true){
                try {
                    myBlockQueue.put(3);
                    System.out.println(Thread.currentThread().getName() + " 放入了 " + 3);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "producer_3");

        Thread consumer_1 = new Thread(() -> {
            while (true){
                try {
                    Integer takeNum = myBlockQueue.take();
                    System.out.println(Thread.currentThread().getName() + " 拿取了 " + takeNum);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer_1");

        Thread consumer_2 = new Thread(() -> {
            while (true){
                try {
                    Integer takeNum = myBlockQueue.take();
                    System.out.println(Thread.currentThread().getName() + " 拿取了 " + takeNum);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer_2");


        consumer_1.start();
        consumer_2.start();
        Thread.sleep(1000);
        System.out.println(consumer_1.getName() + " : " + consumer_1.getState());
        System.out.println(consumer_2.getName() + " : " + consumer_2.getState());
        Thread.sleep(1500);
        producer_1.start();
        producer_2.start();
        producer_3.start();



        // 基于阻塞队列手动写一个定时器
        // 定时器使用的是 PriorityBlockQueue
        // 需要注意的是优先级消息队列用到了堆，同时会使用到 comparable 接口
        // 对于放入这个消息队列的对象需要实现这个接口
        // 优化写出的定时器代码
    }
}
