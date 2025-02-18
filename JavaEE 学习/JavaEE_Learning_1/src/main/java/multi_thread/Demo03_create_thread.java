package multi_thread;

// 日常开发中常见的几种通过内部类创建线程的方式：
// 通过内部类创建线程的方式只适用于在方法本体中使用，即临时需要创建线程，并没有单独抽象化线程 PCB
// 1. 使用 Thread 匿名内部类创建线程
// 2. 使用 Runnable 匿名内部类创建线程
// 3. 使用 Lambda 表达式创建线程

// 第一种方式：使用 Thread 匿名内部类创建线程

public class Demo03_create_thread {
    public static void main(String[] args) {
        Thread mythread = new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Hello, my little thread!");
                }
            }
        };
        mythread.start();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello, my main thread");
        }

    }
}
