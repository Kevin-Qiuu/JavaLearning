package learn.abstract_class;

public class Dog extends Animal{
    public Dog(String dog_name){
        super(dog_name); // 调用父类的构造函数，只能放在构造函数的第一行
        System.out.println("调用 Dog 的构造函数");
    }

    // 重写方法
    @Override
    public void bark(){
        System.out.println("小狗("+animal_name+")在汪汪叫～～");
    }


    @Override
    public void test_fun(){
        System.out.println("调用 Dog 下的 test_fun");
    }
}
