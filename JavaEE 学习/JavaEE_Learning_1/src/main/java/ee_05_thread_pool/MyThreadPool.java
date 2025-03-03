package ee_05_thread_pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {
    int corePoolSize; // 核心线程的个数（火锅店的堂食座位）
    int maxPoolSize; // 全部线程的个数，临时线程个数 = maxPoolSize - corePoolSize （火锅店的全部座位，堂食+户外桌）
    long keepAliveTime; // 临时线程的存活时间（户外小木桌的空客时间）
    TimeUnit unit; // 临时线程存活时间的单位
    BlockingQueue<Runnable> workQueue; // 组织（保存）任务的阻塞队列（火锅店的等客位）
    // 工厂格式和拒绝策略省略...


    public MyThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveTime = keepAliveTime;
        this.unit = unit;
        this.workQueue = workQueue;

        for (int i = 0; i < corePoolSize; i++) {
            int threadId = i + 1;
            Thread poolThread = new Thread(() -> {
                while (true){
                    Runnable task = null;
                    try {
                        task = workQueue.take();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    task.run(); // 在线程中调用 task 的 run 方法
                }
            }, "pool-Thread-" + threadId);
            poolThread.start();
        }
    }

    public void submit(Runnable task) throws InterruptedException {
        workQueue.put(task);
    }


}
