package ee_04_block_queue;

import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.PriorityBlockingQueue;

// 手写一个计时器
// 使用 PriorityBlockQueue 优先级队列

// 定义任务类

abstract class TimerRunTask implements Runnable{}

class MyTimerTask implements Comparable{
    // MyTimerTask cannot be cast to class java.lang.Comparable
    // PriorityBlockingQueue 是通过 comparable 来比较队列元素的优先级
    // 所以要放入这个队列中的类应该实现 comparable
    private long runTime;
    private TimerRunTask runTask;

    public MyTimerTask(TimerRunTask runTask, long runTime) {
        this.runTime = runTime;
        this.runTask = runTask;
    }

    // 如果当前时间未到任务执行时间，则让调用 TaskRun 方法的线程睡眠
    public void TaskRun() throws InterruptedException {
        Thread.sleep(runTime - System.currentTimeMillis());
        runTask.run();
    }

    public long getRunTime(){
        return runTime;
    }

    @Override
    public int compareTo(Object o) {
        MyTimerTask task_1 = (MyTimerTask)o;
        if (runTime < task_1.getRunTime())
            return -1;
        else if (runTime == task_1.getRunTime())
            return 0;
        else
            return 1;
    }
}

public class MyTimer {
    private PriorityBlockingQueue<MyTimerTask> tasksQueue = new PriorityBlockingQueue<>();
    // 定义一个线程一直监控执行阻塞队列中的任务
    private Thread timerThread = new Thread(() -> {
        while (true) {
            MyTimerTask task = null;
            try {
                task = tasksQueue.take();
                task.TaskRun();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }, "TimerThread"); // 用于管理消息队列的线程，随时等待新的任务的注入，然后执行

    public MyTimer() {
        timerThread.start();
    }

    public void schedule(TimerRunTask timerTask, long delay) {
        if (delay < 0)
            throw new IllegalArgumentException("任务的延迟时间不可为负数");
        tasksQueue.put(new MyTimerTask(timerTask, System.currentTimeMillis() + delay));
    }

    public void printTimerThreadState(){
        System.out.println(timerThread.getName() + " : " + timerThread.getState());
    }

}
