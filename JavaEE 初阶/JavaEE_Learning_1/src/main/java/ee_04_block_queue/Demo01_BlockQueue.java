package ee_04_block_queue;

import javax.swing.plaf.synth.SynthListUI;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class MyTimerTask_1 extends TimerTask {
    @Override
    public void run() {
        System.out.println("执行自定义对象的 run 方法...");
    }
}


public class Demo01_BlockQueue {
    public static void main7(String[] args) {
        MyTimer myTimer = new MyTimer();
        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("执行第一个任务...");
            }
        }, 0);

        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("执行第二个任务...");
            }
        }, 0);

        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("执行第三个任务...");
            }
        }, 0);
    }

    public static void main(String[] args) throws InterruptedException {
        MyTimer myTimer = new MyTimer();
        Thread timeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("After 0.5s ...");
            }
        });
        timeThread.start();
        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("天亮了...");
            }
        }, 0);
        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("小邱第二个起床了...");
            }
        }, 10000);
        // 因为先放入了小邱起床的任务，所以此时扫描线程在等待小邱起床任务的执行时间，处于忙等
        // 所以再次添加小球球起床的任务，即使他执行时间更靠前，程序也不能够按时执行小球球起床的任务
        // 程序的实时性就变差了，但是在扫描线程结束 WAITING 时，仍然会拿最靠前的任务（因为 PriorityBlockingQueue），
        // 所以执行任务的顺序不会发生改变，但是任务的总体执行实时性变差了
        Thread putTaskDemo = new Thread(() -> {
            putTimeTask(myTimer, new TimerRunTask() {
                @Override
                public void run() {
                    System.out.println("小球球起床了...");
                }
            }, 1000);
        }, "putTaskDemo");
        putTaskDemo.start();
        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("小陈第三个起床了...");
            }
        }, 15000);
    }

    public static void putTimeTask(MyTimer myTimer, TimerRunTask task, long delay) {
        myTimer.schedule(task, delay);
    }

    public static void main4(String[] args) {

    }

    public static void main3(String[] args) throws InterruptedException {
        // 基于阻塞队列手动写一个定时器
        // 定时器使用的是 PriorityBlockQueue
        // 需要注意的是优先级消息队列用到了堆，同时会使用到 comparable 接口
        // 对于放入这个消息队列的对象需要实现这个接口
        // 优化写出的定时器代码

        MyTimer myTimer = new MyTimer();
        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("天亮了....");
            }
        }, 500);

        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("小明第一名起床...");
            }
        }, 1000);

        myTimer.schedule(new TimerRunTask() {
            @Override
            public void run() {
                System.out.println("小红第二名起床...");
            }
        }, 1500);


        Thread threadTask = new Thread(() -> {
            myTimer.schedule(new TimerRunTask() {
                @Override
                public void run() {
                    System.out.println("小邱第三名起床...");
                }
            }, 2000);
            TimerRunTask myTimerRunTask = new TimerRunTask() {
                @Override
                public void run() {
                    System.out.println("小陈最后一名起床...");
                }
            };
            myTimer.schedule(myTimerRunTask, 2500);
        }, "threadTask");

        threadTask.start();

        Thread.sleep(3000);
        myTimer.printTimerThreadState();
    }

    public static void main2(String[] args) {

        // 使用 JDK 创建一个定时器 timer
        Timer timer = new Timer();
        // TimerTask 是一个实现了 Runnable 接口的类
        // 要自行定义 run 方法，然后注册到 timer 的 schedule 中
        // 这里使用自定义对象和匿名类创建对象
        MyTimerTask_1 myTimerTask = new MyTimerTask_1();
        timer.schedule(myTimerTask, 500);
        // System.currentTimeMillis()+delay 源码中的 delay 表示从程序运行开始算起+delay，为 myTimerTask 的运行时间
        // timer 会调用传递的 TimerTask 的 run 方法
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行匿名类创建的匿名对象的 run 方法...");
            }
        }, 1000);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行匿名类创建的 timerTask 对象的 run 方法...");
            }
        };
        timer.schedule(timerTask, 1500);
        // timer 实则是一个线程，一直在等待新的任务注入，所以在当前消息阻塞队列为空时
        // 会进入 WAITING 状态阻塞
        // private final TimerThread thread = new TimerThread(queue);
        // 会在 Timer 中会有一个线程的成员变量

        Thread thread_timer = new Thread(() -> {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "的 run 方法开始被执行...");
                }
            }, 2000);
        }, "thread_timer");
        thread_timer.start();

    }

    public static void main1(String[] args) throws InterruptedException {
        // 再实现一遍消费者生产者的案例吧
        // 使用 while 判断是否已满
        // API：take（拿取）
        // API：put （放入）
        // 满的时候使放入消息队列的线程阻塞
        // 空的时候使拿取消息队列的线程阻塞

        MyBlockQueue myBlockQueue = new MyBlockQueue(-1);
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
            while (true) {
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
            while (true) {
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
            while (true) {
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
            while (true) {
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


    }
}
