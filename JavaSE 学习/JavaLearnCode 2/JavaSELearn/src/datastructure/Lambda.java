package datastructure;

public class Lambda {
    public static void main(String[] args) {
        // 1. 使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello, anonymous inner class!");
            }
        }).start();

        // 2. 使用 Lambda 表达式
        new Thread(() -> {
            System.out.println("Hello, Lambda!");
        }).start();

        // 3. 使用 Lambda 表达式简化
        new Thread(() -> System.out.println("Hello, Lambda simplified!")).start();


     }
}
