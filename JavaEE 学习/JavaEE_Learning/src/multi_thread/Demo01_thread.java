package multi_thread;

class MyThread extends Thread{
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
}

public class Demo01_thread{
    public static void main(String[] args) {
         // main 是主线程
        MyThread myThread = new MyThread();
        // 开启另外一个线程
        myThread.start();
        // 继续主线程的任务
        while (true){
            try {
                Thread.sleep(1000); // 使当前线程睡眠 1000 毫秒
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello!!!!!!!!");
        }
    }

}
