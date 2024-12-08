package learnSE.abstract_class;

public class Test_main {

    public static void test_bark(Animal animal){
        // 父类的形参引用接受子类对象，形成向上转型
         animal.bark(); // 构成动态绑定
    }

    public static void main(String[] args) {
        Dog dog = new Dog("旺财");
        Cat cat = new Cat("奶茶");
        Tiger tiger = new Tiger("脑斧");
        test_bark(dog);
        test_bark(cat);
        test_bark(tiger);

        // 预计的输出：
        // 调用 Animal 的构造函数
        // 调用 Dog 下的 test_fun
        // 调用 Dog 的 构造函数
        // 。。。。
        // 小狗(旺财)在汪汪叫～～
        // 。。。


    }
}
