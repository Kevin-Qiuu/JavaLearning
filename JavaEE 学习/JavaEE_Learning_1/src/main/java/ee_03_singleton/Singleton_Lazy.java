package ee_03_singleton;

public class Singleton_Lazy {
    // 懒汉
    // 延时加载
    // 为了降低内存消耗，在程序刚开始运行时不去创建单例
    // 而是在需要使用单例的时候再去创建
    // 所以一开始定义为 null，在第一次获取单例进行访问时，再去创建

    // 只要在会多线程环境中被修改的共享变量，就要添加 volatile 关键字
    private static volatile Singleton_Lazy singletonLazy = null;
    public String info;

    private Singleton_Lazy(){
        info = "Hello, Singleton_Lazy!";
    }

    public static Singleton_Lazy getInstance(){
        // 这种锁在起初创建单例的时候可以解决线程安全问题
        // 但是在后期其他线程需要调用时，会重复对这行代码块上锁，导致锁资源的浪费
        // 所以在后期单例创建完毕后，要对单例进行一次判断，如果已经存在了，就不用执行竞争锁资源
        // 进而减轻了内存的消耗，这就是 DCL(Double check lock) 双检查锁
        if (singletonLazy == null) {
            synchronized (Singleton_Lazy.class) {
                if (singletonLazy == null) {
                    // 达到了延时加载
                    System.out.println("第一次访问单例，懒汉单例即将创建...");
                    singletonLazy = new Singleton_Lazy();
                }
            }
        }
        return singletonLazy;
    }
}
