package learn.cls_and_obj;
// 包访问权限
// package com.baidu.www.Test
// 只有在当前包所处的目录下的类才可以

// * 并不会导入util下的全部包，而是在使用对应包的时候对其进行导入调用

class Person{
    public static int a = 10;
    public String name;
    public int perID;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", perID=" + perID +
                '}';
    } // 触发了多态，这是我的薄弱项

    // 静态代码块只会执行一次，在程序一开始就被调用了
    // 也可以理解为在创建完静态成员变量时就会被调用了
    // 可以有多个静态代码块，会按照定义的顺序先后执行（直接合并执行）
    static  {
        System.out.println("静态局部整型变量 a = " + a);
        System.out.println("Person 的构造代码块");
    }

    static {
        System.out.println("这是另外一个Person的代码块");
    }

    public Person(){
        System.out.println("Person 的构造方法");
    }

    public Person(String name, int perID){
        // 当成员变量与成员方法的参数变量重名时，优先选择参数变量
        // 所以，直接用this最好
        this.name = name;
        this.perID = perID;
    }
}


public class Test_static {
    int a = 10;
    // 经 default 访问修饰限定符修饰的变量
    // 在同一个包中的所有类都可以访问，其他包中的类不可以访问
    // 即 包访问权限
    public int b;

    public static int c;
    // 对于 static 访问修饰限定符修饰的
    // 是该类下全部的对象共有的
    // 但注意 静态成员方法 是没有办法传递this的
    // 符合逻辑，因为其是共享的，不是某一个对象所特有的，是整个类的
    // 静态成员在程序起初就被创建到内存空间了
    // Java中没有局部静态变量（这与 c++ 是不同的）



    public Test_static(){
        System.out.println("Test 的构造方法");
    }


    public static void main(String[] args) {
        Person p_1 = new Person("邱俊航",202406);
        Person p_2 = new Person("邱俊俊",202408);
        System.out.println(p_1);
        System.out.println(p_2);
    }
}
