package ee_02_thread_safety;

import java.util.Scanner;

public class Demo05_volatile {
    static int flag = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread myThread_01 = new Thread(() -> {
            while (flag == 0){

            }
            System.out.println(Thread.currentThread().getName() + " has been exited...");
        }, "myThread_01");

        Thread myThread_02 = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input a number > ");
            flag = scanner.nextInt();
            System.out.println(Thread.currentThread().getName() + " report: " +"flag = "+flag);
            System.out.println(Thread.currentThread().getName() + " has been exited...");
        }, "myThread_02");

        Thread myThread_03 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start...");
            System.out.println(Thread.currentThread().getName() + " report: " +"flag = "+flag);
            System.out.println(Thread.currentThread().getName() + " has been exited...");
        }, "myThread_03");

        myThread_01.start();
        myThread_02.start();
        myThread_02.join(); // 验证线程 2 是否会将 flag 写入主内存中
        myThread_03.start();

    }
}
