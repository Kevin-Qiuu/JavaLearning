package ee_02_thread_safety;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. 尝试使用了 synchronized 修饰的成员方法来使多个线程争抢锁
 * 2. 使用 synchronized 修饰的代码块来使多个线程争抢锁
 */

class LOCKER{
    public synchronized void wait_thread() throws InterruptedException {
        this.wait();
    }

    public synchronized void wake_thread(){
        this.notify();
    }

}

class ProducerConsumer{
    public List<Integer> buffer = new ArrayList<>();
    int capacity = 5;

    public synchronized void produce(int value, String name) throws InterruptedException {
        while (buffer.size() >= capacity){
            wait();
            // 如果 buffer 里面满了，生产者线程暂停任务
            // 等待消费者线程消费后再将其唤醒继续执行生产任务
        }

        // 如果使用的是 if 语句而不是 while 语句，会出现线程安全问题：
        // 在buffer 满了以后，两个生产者线程都会经过 if 条件判断进入 WAITING 状态
        // 但是在消费者消费了一个后，唤醒了全部的生产者线程，此时都会继续执行下面的生产任务
        // 而没有去判断是否已经有另一个唤醒后的生产者线程已经生产一个了，所以出现了线程安全问题
        // 所以在唤醒后要立即再进入判断是否是满的
        // 因为有 synchronized 在，保证了原子性，所以线程安全问题解决了
        // 多线程功力 + 1

        buffer.add(value);
        System.out.println(name + " : " + "Produced: " + value);
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        while (buffer.isEmpty()){
            wait();
            // 如果 buffer 空了，消费者线程暂停任务
            // 等待生产者线程生产后将其唤醒继续执行消费任务
        }

        Integer con_num = buffer.remove(0);
        System.out.println("Consumed: " + con_num);
        notifyAll();
    }

}

public class Demo06_wait_notifyall {
    static Object locker = new Object();

    // 生产者 & 消费者模型
    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();

        // 创建一个线程来生产材料（生产者）
        Thread producer_thread_1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    pc.produce(i, "producer_thread_1");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "producer_thread_1");

//         为什么多加了一个会出现线程不安全问题？
        Thread producer_thread_2 = new Thread(() -> {
            for (int i = 10; i < 20; i++) {
                try {
                    pc.produce(i, "producer_thread_2");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "producer_thread_2");

        // 创建一个线程来消费材料（消费者）
        Thread consumer_thread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    pc.consume();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer_thread");

        Thread thread_01 = new Thread(() -> {
            while (true){
                System.out.println("pc.buffer.size: " + pc.buffer.size());
                System.out.println(producer_thread_1.getName() + " : " + producer_thread_1.getState());
//                System.out.println(producer_thread_2.getName() + " : " + producer_thread_2.getState());
                System.out.println(consumer_thread.getName() + " : " + consumer_thread.getState());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }, "thread_01");

        thread_01.start();
//        Thread.sleep(200);
        producer_thread_1.start();
        producer_thread_2.start();
        Thread.sleep(8000);
        consumer_thread.start();
    }


    public static void main2(String[] args) throws InterruptedException {
        LOCKER locker1 = new LOCKER();
        Thread thread_01 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            try {
                locker1.wait_thread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " has been exited...");
        }, "thread_01");

        Thread thread_02 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            locker1.wake_thread();
        }, "thread_02");

        System.out.println(thread_01.getName() + " : " + thread_01.getState());
        System.out.println(thread_02.getName() + " : " + thread_02.getState());
        thread_01.start();
        Thread.sleep(500); // 等待 thread_01 创建线程 PCB
        System.out.println(thread_01.getName() + " : " + thread_01.getState());
        System.out.println(thread_02.getName() + " : " + thread_02.getState());
        Thread.sleep(1000);
        thread_02.start();
        Thread.sleep(500); // 等待 thread_02 唤醒线程 PCB
        System.out.println(thread_01.getName() + " : " + thread_01.getState());
        System.out.println(thread_02.getName() + " : " + thread_02.getState());
    }

    public static void main1(String[] args) throws InterruptedException {
        Thread thread_01 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " has been started...");
            synchronized (locker){
                System.out.println(Thread.currentThread().getName() + " will be waiting...");
                try {
                    locker.wait(); // 通过锁对象作为中间环节来进行线程之间的等待与唤醒的通信
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " exit...");
            }
        }, "thread_01");

        Thread thread_02 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " has been started...");
            synchronized (locker){
                System.out.println(Thread.currentThread().getName() + " will be waiting...");
                try {
                    locker.wait(); // 通过锁对象作为中间环节来进行线程之间的等待与唤醒的通信
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " exit...");
            }
        }, "thread_02");

        Thread thread_03 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " has been started...");
            synchronized (locker){
//                locker.notify(); // 唤醒全部被 locker 设为 WAITING 的线程
                // notify() 会唤醒被 locker 锁住的其中某一个线程，但具体是哪一个不确定
                // notifyAll() 会唤醒全部被 locker 锁住的线程
                locker.notifyAll();
            }
            System.out.println(Thread.currentThread().getName() + " has notified all waiting threads...");

        }, "thread_03");

        thread_01.start();
        thread_02.start();

        Thread.sleep(500);

        System.out.println(thread_01.getName() + " : " + thread_01.getState());
        System.out.println(thread_02.getName() + " : " + thread_02.getState());

        thread_03.start();

        Thread.sleep(500);
        System.out.println(thread_01.getName() + " : " + thread_01.getState());
        System.out.println(thread_02.getName() + " : " + thread_02.getState());
    }
}
