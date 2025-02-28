package ee_01_multi_thread;

public class Demo06_interrupt_thread {
    // 第一种，设置全局变量自定义中断标志位
    static boolean isQuit = false;
    public static void main1(String[] args) throws InterruptedException {
        Thread myThread = new Thread(() -> {
            while (!isQuit){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello, my little thread!");
            }
        }, "myThread");

        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        myThread.start();
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        System.out.println(myThread.getName() + "将在 5s 钟后触发中断标志位。");
        Thread.sleep(5000);
        isQuit = true;
        System.out.println(myThread.getName() + "中断标志位已触发。");
        // 中断标志位触发的时候，线程极大概率正处于休眠状态，所以还会再打印一条信息。
        Thread.sleep(1000);
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());

    }

    // 第二种，调用 Thread.interrupt() 方法
    // 调用 interrupt() 方法中断正常线程（未使用 sleep 休眠方法的进程）
    public static void main2(String[] args) throws InterruptedException {
        Thread myThread = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("Hello, my little thread!");
            }
        }, "myThread");
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        myThread.start();
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        System.out.println(myThread.getName() + "将在 5s 钟后触发中断标志位。");
        Thread.sleep(5000);
        myThread.interrupt();
        System.out.println(myThread.getName() + "中断标志位已触发。");
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
    }

    // 调用 interrupt() 方法中断使用 sleep 休眠方法的线程
    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(()->{
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
//                    e.printStackTrace();
                    // 当中断处于 sleep 状态的线程时，会触发此异常
                    // 此时不可正常中断线程，需要在捕获异常处的代码块内，手动中断
                    System.out.println("触发了中断异常 (InterruptedException)");
                    System.out.println("重新中断线程");
                    Thread.currentThread().interrupt();
                }
                System.out.println("Hello, my little thread!");
            }
        }, "myThread");
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        myThread.start();
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
        System.out.println(myThread.getName() + " 将在 2s 钟后触发中断标志位。");
        Thread.sleep(2000);
        myThread.interrupt();
        System.out.println(myThread.getName() + "中断标志位已触发。");
        System.out.println(myThread.getName() + " 是否存活：" + myThread.isAlive());
    }
}
