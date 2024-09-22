package learn.abstract_class;

public abstract class Animal {
    String animal_name;

    public Animal(String animal_name){
        this.animal_name = animal_name;
        System.out.println("调用 Animal 的构造函数");
        test_fun();
        // 在触发动态绑定后，不会调用自己的，因为子类已经重写了
        // 方法表的地址已经改变
    }

    // 触发动态绑定的方法
    public abstract void bark();

    public void test_fun(){
        System.out.println("调用 Animal 下的 test_fun");
    }

    // instanceof

    // 重载和重写的区别？？？
    // 方法的重载是一个类中方法之间的联系，构成重载的条件是
    // 方法名相同，方法的参数类型不同，与方法返回值无关
    // 这是有 Java 方法签名机制决定
    //
    // 方法的重写是子类与父类方法之间的联系，构成重写的条件是
    // 方法名相同，方法的参数列表不同，方法的返回值相同（但子类方法可以返回父类方法返回值的父类）
    // 通过使用 abstract 类、implement 接口，形成完备的开发规范
    // 可以触发动态绑定，形成多态的效果
    //
    // 动态绑定的条件
    // 1、向上转型
    // 2、子类重写父类方法
    // 3、通过父类引用接收子类变量，进行调用重写方法

    // 围绕重载是一个类中的方法之间的联系
    // 重写是子类与父类中方法之间的联系
}
