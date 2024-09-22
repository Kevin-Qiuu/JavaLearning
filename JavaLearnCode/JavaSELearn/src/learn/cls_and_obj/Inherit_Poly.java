package learn.cls_and_obj;

class Animal{
    String name;
    int age;
    int aniID;

    public Animal(){
        System.out.println("Animal 无参构造函数调用");
    }

    public Animal(String name, int age, int aniID){
        this.name = name;
        this.age = age;
        this.aniID = aniID;
        System.out.println("Animal 有参构造函数调用");
    }

    public void shout(){
        System.out.println("动物在喊叫");
    }

    public void eat(){
        System.out.println("动物喜欢吃什么");
    }

}

class Dog extends Animal{
    boolean hasTakenShit;

    public Dog(){
        super(); // 使用 super 调用基类的构造函数，必须放到子类构造函数代码首行
        hasTakenShit = false;
        System.out.println("调用了 Dog 的无参构造函数");
    }

    public Dog(String name, boolean hasTakenShit){
        super(name, 5,10);
        System.out.println("调用了 Dog 类的 有参构造函数");
    }

    // 使用重写，为触发多态做准备
    // 撰写注解，以让系统明确下面方法会被重写
    // 进而系统会去检查基类是否存在所重写的方法签名
    // 不存在则报错，注意这里说的是方法签名
    @Override
    public void eat(){
        // 重写基类的 eat 方法
        System.out.println("小狗"+name+"喜欢吃骨头和狗粮（调用了 Dog 类的 eat 重写方法）");
    }
}

class Cat extends Animal{
    int eatFishNum;

    public Cat(){
        super();
        eatFishNum = 0;
        System.out.println("调用了 Cat 的无参构造函数");
    }

    public Cat(String name, int eatFishNum){
        super(name,5,14); // 调用基类有参构造函数
        this.eatFishNum = eatFishNum;
        System.out.println("调用了 Cat 的有参构造函数");
    }

    @Override
    public void eat(){
        // 重写了基类的 eat 方法
        System.out.println("小猫"+name+"吃了"+eatFishNum+"条小鱼（调用了 Cat 类的 eat 重写方法）");
    }
}


// 继承和多态的学习
public class Inherit_Poly {
    // 尝试多态的代码尝试
    // 还没有理解多态的实现原理
    // 多态的实现原理大致如下：
    // 在 Java 中，所有类都有一个特有的方法表，用于找到对应的方法
    // 可以理解为 JVM 管理一个类所有方法的表
    // 继承了一个基类的子类，在重写父类方法时，
    // 直接将父类的方法表中所对应的这个方法的引用改动了
    // 换为子类对这个方法改动后的方法的引用
    // 所以再进行向上转移时，将一个子类变量通过基类去引用
    // 则只可通过基类引用访问基类的内容，在调用重写方法时，就形成了多态
    // 多态实际就是用户（程序员）在使用时，对这一代码场景的描述
    // 本质就是子类对基类方法的重写，改动了子类中方法表中基类部分方法表的偏移量
    // 进而实现了不同的子类通过它们全部的基类引用调用，对同一行为呈现了不同的动作


    // 一个会触发多态的方法
    // 触发多态的规则：
    // 1、必须在继承体系下
    // 2、子类必须要对父类方法重写
    // 3、通过父类的引用重写子类方法

    // 对于多态的个人理解：
    // 一个基类下的不同子类在面对同样的行为时，会有不一样的结果产生，这就是多态
    // 比如同为打印机的彩印机和黑白印机，对于打印行为，彩印机印出彩色，黑白印机引出白色
    // 一个弓箭，在同一箭尾下，拴上不同的箭头会有不同的威力 
    // 拴上火箭头，会着火，拴上挂有物资的箭头，会给予物资
    // 但他们都是弓箭，在搭载不一样的箭头下，会有不一样的结果

    // 触发多态的条件（触发动态绑定的条件）
    // 动态绑定指的意思是父类引用变量在程序运行过程中会动态指向不同子类的变量
    // 这个指向的动作称为绑定，而根据具体程序去指向（即起初并不知道会指向什么子类变量）
    // 便称为动态绑定
    // 1、有继承关系
    // 2、向上转型（用一个父类引用变量指向一个子类的动作 ）
    // 3、构成方法重写





    public static void animalEatWhat(Animal animal){
        animal.eat();
    }

    public static void main(String[] args) {
        Dog dog = new Dog("旺财",true);
        Cat cat = new Cat("奶茶",30);
        animalEatWhat(dog);
        animalEatWhat(cat);
        System.out.println("==================");
        System.out.println("还是不晓得多态的实现原理");
    }
}
