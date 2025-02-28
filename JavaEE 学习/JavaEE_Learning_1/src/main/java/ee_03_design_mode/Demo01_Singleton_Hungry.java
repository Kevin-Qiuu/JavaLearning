package ee_03_design_mode;

// 单例模式
// 在整个程序的运行过程中，只有一个对象，并且不可以 new 新的
public class Demo01_Singleton_Hungry {
    // 饿汉
    // 迫不及待，程序一启动就直接创建单例

    // private 即不可以再类外访问这个引用，防止其他地方更改了这个变量指向的对象
    // static 保证了这个变量全局都只有一个，因为 Demo14_Singleton_Hungry.class 是全局唯一的对象
    // 被 static 修饰的变量在 Demo14_Singleton_Hungry.class 创建时就会被创建
    private static Demo01_Singleton_Hungry singletonHungry = new Demo01_Singleton_Hungry();
    public String info;
    // 私有化构造方法，防止在类外 new 这个对象
    private Demo01_Singleton_Hungry() {
        info = "Hello, SingleHungry!";
    }

    // 定义 getInstance 方法，使得类外可以访问这个变量
    // 要用 static 修饰，因为规定了类外不可创建对象，进而不可以获取成员方法
    // 所以要通过类访问静态成员方法
    public static Demo01_Singleton_Hungry getInstance(){
        return singletonHungry;
    }

}
