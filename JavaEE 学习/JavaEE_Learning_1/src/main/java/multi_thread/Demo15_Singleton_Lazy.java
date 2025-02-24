package multi_thread;

public class Demo15_Singleton_Lazy {
    // 懒汉
    // 延时加载
    // 为了降低内存消耗，在程序刚开始运行时不去创建单例
    // 而是在需要使用单例的时候再去创建
    // 所以一开始定义为 null，在第一次获取单例进行访问时，再去创建

    private static Demo15_Singleton_Lazy singletonLazy = null;
    public String info;

    private Demo15_Singleton_Lazy(){
        info = "Hello, Singleton_Lazy!";
    }

    public static Demo15_Singleton_Lazy getInstance(){
        synchronized (Demo15_Singleton_Lazy.class) {
            if (singletonLazy == null) {
                // 达到了延时加载
                System.out.println("第一次访问单例，懒汉单例即将创建...");
                singletonLazy = new Demo15_Singleton_Lazy();
            }
        }
        return singletonLazy;
    }
}
