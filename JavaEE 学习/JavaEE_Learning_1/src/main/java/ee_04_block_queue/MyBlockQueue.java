package ee_04_block_queue;

import java.util.ArrayList;

public class MyBlockQueue {
    // 凡是会在多线程中修改的变量，都要加 volatile，以保证线程安全
    private volatile int head = 0; // 队列头
    private volatile int tail = 0; // 队列尾
    private volatile int size = 0; // 队列当前的容量
    private int capacity; // 队列最大的容量
    private ArrayList<Integer> blockQueue = null; // 循环队列

    public MyBlockQueue(int capacity){
        this.capacity = capacity;
        blockQueue = new ArrayList<>(capacity);
    }

    // 为了避免多线程同时向 blockqueue 添加数据
    // 所以使用 synchronized  关键字保证代码的原子性、内存可见性
    public synchronized void put(Integer value) throws InterruptedException {
        while(size == capacity){
            this.wait();
        }

        blockQueue.add(value);

        ++tail; ++size;
        // 处理 tail 超过了索引最大长度的情况
        if (tail >= capacity)
            tail = 0;

        // 唤醒因队列为空却还在取数据的 take 线程
        this.notifyAll();
    }

    public synchronized Integer take() throws InterruptedException {
        // 如果使用 if 会引发线程安全问题
        // 当多个线程同时从阻塞状态被唤醒来取数据时，会继续执行 wait() 方法后的逻辑
        // 所以在一个线程抢到锁后，可能就会使得 blockQueue 变成空的状态
        // 那下一个线程在抢到锁后，没有再次判断 blockQueue 是否是空的，便会引发线程安全问题
        // 所以为了线程安全，每次取放数据都要经过一次条件判断，if 语句只能保证从头调用方法时可以判断
        // 但是不能保证调用完 wait() 方法后再检查条件，而 while 解决了这个问题
        while (size == 0){
            this.wait();
        }

        Integer takeNum = blockQueue.get(head);

        ++head; --size;
        // 处理 head 超过了索引最大长度的情况
        if (head >= capacity)
            head = 0;

        this.notifyAll();
        return takeNum;
    }
}
