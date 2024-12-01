package datastructure;

import java.text.MessageFormat;
import java.util.Set;

class Plate<T>{
    T thing;

    public T getThing() {
        return thing;
    }

    public void setThing(T thing) {
        this.thing = thing;
    }
}



public class TongPeiFu {
    public static void main(String[] args) {
        System.out.println("Hello, TongPeiFu!");
    }


    // public static void func(Map<T,J> myMap){}
    // 以上是错误示范
    // Map<T,J> 是泛型，不能直接使用
    // 泛型是在编译时期进行类型检查的，编译时期泛型会被擦除

    public static void func(Set<Apple> mySet) {
        // ？是通配符，表示任意类型
        // 在这里使用到了通配符的上界，表示最高可以是 Fruit 类型
        mySet.add(new Apple("HongFushi", 25,1));
    }

    public static void func2(Plate<? extends Food> myPlate){
        // 上面声明了要接收的通配符上界是 Fruit，而这里传递的是 Food 的子类，并且 Food 不是 Fruit 的子类
        // 所以出错，所以在这里，通配符的上界可以使方法调用者明确传递参数的规则
        // myPlate.setThing(new Meat("ZhuRou",20));
        // 此时无法在fun函数中对temp进行添加元素，因为temp接收的是Fruit和他的子类，
        // 此时存储的元素应该是哪个子类无法确定。所以添加会报错！但是可以获取元素，因此同样会报错
        // myPlate.setThing(new Apple("Hongfushi",25,1));
        // 下面这行代码也会报错，原因同样是无法确认通配符表示的是哪一个子类，所以必须使用通配符的上界或者上界的父类来接收
        // Apple myFood = myPlate.getThing();
        Food myFood = myPlate.getThing();
        Object myObject = myPlate.getThing();
    }
}
