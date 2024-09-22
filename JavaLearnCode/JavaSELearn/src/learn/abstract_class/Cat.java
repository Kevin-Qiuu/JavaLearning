package learn.abstract_class;

public class Cat extends Animal{
    public Cat(String cat_name){
        super(cat_name); // 调用父类的构造函数，只能放在构造函数的第一行
        System.out.println("调用 Cat 的构造函数");
    }

    // 重写方法
    @Override
    public void bark(){
        System.out.println("小猫("+animal_name+")在喵喵叫～～");
    }

    @Override
    public void test_fun(){
        System.out.println("调用 Cat 下的 test_fun");
    }
}
