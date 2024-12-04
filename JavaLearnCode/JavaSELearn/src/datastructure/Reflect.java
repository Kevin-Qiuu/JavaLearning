package datastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class HelloTest{
    private int a;
    private int b;

    public HelloTest(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    private void printHello(){
        System.out.println(a);
        System.out.println("Hello, private method!");
        System.out.println(this.hashCode());
    }

}

public class Reflect {
    public static void main(String[] args) {
    }
    public static void main1(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        // 练习反射的用法，获取类的信息
        // 可以将发射视为是一个B超探头，可以探测到类的所有信息
        // 但是不能直接操作类的属性和方法，需要通过反射来操作
        // 1. 获取类的类对象
        // 1.1 通过类名.class
        Class<?> cls1 = HelloTest.class;
        // 1.2 通过对象.getClass()
        HelloTest ht = new HelloTest(520,2);
        Class<?> cls2 = ht.getClass();
        // 1.3 通过Class.forName() 这个方法会抛出异常，要使用异常处理
        Class<?> cls3 = Class.forName("datastructure.HelloTest");
        System.out.println(cls3);
        System.out.println(cls3 == cls1);
        System.out.println(cls3 == cls2);

        // 2. 获取类的属性
        // 2.1 获取类的所有属性
        Field[] fields = cls1.getDeclaredFields();
        fields[0].setAccessible(true); // 对于私有属性，需要设置为可访问
        // 获取属性的值与名字
        System.out.println(fields[0].getName());
        System.out.println(fields[0].get(ht));
        // 2.2 获取类的方法
        // 方法签名如下：public Constructor<T> getConstructor(Class<?>... parameterTypes)
        // 所以传递的是可变参数，并且类型是 Class<?> 类型
        Constructor<?> helloTestConstructor = cls1.getConstructor(int.class, int.class);
        // 通过构造器来创建对象
        HelloTest ht2 = (HelloTest) helloTestConstructor.newInstance(521, 2);
        System.out.println(ht2.getA());
        // 2.3 获取类的方法
        Method printHelloMethod = ht2.getClass().getDeclaredMethod("printHello");
        printHelloMethod.setAccessible(true);
        // 通过方法对象来调用方法
        printHelloMethod.invoke(ht2);
        printHelloMethod.invoke(ht); // 效果与通过对象调用方法一样

//        HelloTest[] arr = new HelloTest[10];
    }
}
