package ee_05_thread_pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Demo_01_thread_pool {
    // 表示火锅店有 10 个等饭位
    public static ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(150);

    public static void main2(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(3,10);
        for (int i = 0; i < 100; i++) {
            int taskId = i;
            myThreadPool.submit(()->{
                System.out.println("执行任务：" + taskId + ", " + Thread.currentThread().getName());
            });
        }
    }

    public static void main(String[] args) {
        /*
           public ThreadPoolExecutor(int corePoolSize,  // 核心线程数量
                                     int maximumPoolSize, // 最大线程数量
                                     long keepAliveTime, // 临时线程的存活时间
                                     TimeUnit unit, // 临时时间存活时间的单位
                                     BlockingQueue<Runnable> workQueue, // 组织（保存）任务的阻塞队列
                                     ThreadFactory threadFactory,  // 创建线程的工厂
                                     RejectedExecutionHandler handler)  // 拒绝策略（有四种）
         */
        // 使用 JDK 自定义一个线程池
        // 核心线程：3  最大线程数：5  临时线程最长存活时间：3 秒  任务阻塞队列：workQueue  拒绝策略：直接拒绝（粗暴抛出异常）
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(3, 5, 3,
                TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.AbortPolicy());
//                TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.DiscardPolicy());
//                TimeUnit.SECONDS, workQueue, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 100; i++) {
            int taskId = i + 1;
            threadPool.submit(() -> {
                System.out.println("执行任务：" + taskId + ", " + Thread.currentThread().getName());
            });
        }

    }
}
