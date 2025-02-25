package multi_thread;

public class Demo16_Singleton {
    public static void main(String[] args) {
        // 创建 10个 线程，依次获取单例对象，查看获取结果
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
                System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
            });
            thread.start();
        }
    }
    public static void main3(String[] args) {
        // 多线程访问单例（懒汉）
        Thread thread_1 = new Thread(() -> {
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
        }, "thread_1");

        Thread thread_2 = new Thread(() -> {
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
        }, "thread_2");

        Thread thread_3 = new Thread(() -> {
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
        }, "thread_3");

        Thread thread_4 = new Thread(() -> {
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
        }, "thread_4");

        Thread thread_5 = new Thread(() -> {
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
        }, "thread_5");

        Thread thread_6 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Demo15_Singleton_Lazy singletonLazy = Demo15_Singleton_Lazy.getInstance();
            System.out.println(Thread.currentThread().getName() + " : " + singletonLazy);
        }, "thread_6");

        thread_1.start();
        thread_2.start();
        thread_3.start();
        thread_4.start();
        thread_5.start();
        thread_6.start();
    }
    public static void main2(String[] args) {
        // 使用单例（懒汉）
        Demo15_Singleton_Lazy singletonLazy1 = Demo15_Singleton_Lazy.getInstance();
        Demo15_Singleton_Lazy singletonLazy2 = Demo15_Singleton_Lazy.getInstance();
        Demo15_Singleton_Lazy singletonLazy3 = Demo15_Singleton_Lazy.getInstance();
        Demo15_Singleton_Lazy singletonLazy4 = Demo15_Singleton_Lazy.getInstance();
        Demo15_Singleton_Lazy singletonLazy5 = Demo15_Singleton_Lazy.getInstance();

        System.out.println(singletonLazy1);
        System.out.println(singletonLazy2);
        System.out.println(singletonLazy3);
        System.out.println(singletonLazy4);
        System.out.println(singletonLazy5);

    }
    public static void main1(String[] args) {
        // 使用单例（饿汉）
        // 报错，因为构造函数有 private 权限
//        Demo14_Singleton_Hungry singletonHungry = new Demo14_Singleton_Hungry();
        Demo14_Singleton_Hungry singletonHungry = Demo14_Singleton_Hungry.getInstance();
        System.out.println(singletonHungry.info);
        Demo14_Singleton_Hungry singletonHungry1 = Demo14_Singleton_Hungry.getInstance();
        Demo14_Singleton_Hungry singletonHungry2 = Demo14_Singleton_Hungry.getInstance();
        Demo14_Singleton_Hungry singletonHungry3 = Demo14_Singleton_Hungry.getInstance();
        Demo14_Singleton_Hungry singletonHungry4 = Demo14_Singleton_Hungry.getInstance();
        Demo14_Singleton_Hungry singletonHungry5 = Demo14_Singleton_Hungry.getInstance();
        // 打印引用信息，判断指向的是否为一个对象
        System.out.println(singletonHungry);
        System.out.println(singletonHungry1);
        System.out.println(singletonHungry2);
        System.out.println(singletonHungry3);
        System.out.println(singletonHungry4);
        System.out.println(singletonHungry5);

    }
}
