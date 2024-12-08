package learnSE.abstract_class;

public class Tiger extends Animal{
    public Tiger(String tiger_name){
        super(tiger_name); // 调用父类的构造函数，只能放在构造函数的第一行
        System.out.println("调用 Tiger 的构造函数");
    }

    // 重写方法
    @Override
    public void bark(){
        System.out.println("小老虎("+animal_name+")在嗷嗷叫～～");
    }

    @Override
    public void test_fun(){
        System.out.println("调用 Tiger 下的 test_fun");
    }
}
