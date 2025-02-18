package multi_thread;

// Runnable 是一个只有一个方法的接口，因此是一个函数式接口，对于函数式接口的重写可以使用 Lambda 表达式
// 继承了 Runnable，重写 run 方法，表示这个类可以通过创建线程来去调用其业务逻辑，
// Runnable 通过把 Thread 要志向的业务逻辑抽象化，实现了线程与业务逻辑接口的解耦，（高内聚，低耦合）
class Mythread implements Runnable{
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello, my little thread!");
        }
    }
}

public class Demo02_thread{
    public static void main(String[] args) {
        // 主线程
        // 创建线程所要进行的业务逻辑对象
        Mythread mythread = new Mythread();
        // 创建线程 PCB
        Thread demoThread = new Thread(mythread);
        // 开启线程
        demoThread.start();
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello, my main Thread!");
        }


    }
}
