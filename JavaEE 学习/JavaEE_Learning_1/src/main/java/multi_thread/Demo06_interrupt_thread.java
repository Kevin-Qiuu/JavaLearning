package multi_thread;

public class Demo06_interrupt_thread {
    // 第一种，设置全局变量自定义中断标志位
    static boolean isQuit = false;

    public static void main(String[] args) {
        Thread myThread = new Thread(() -> {
            while (!isQuit){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello, my little thread!");
            }
        });
    }
}
