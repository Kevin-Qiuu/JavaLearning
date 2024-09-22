package learn.cls_and_obj;

public class Student {
    // 定义public 表示可以在类外访问
    // 类外的意思即在这个类的定义域之外
    public int stuID;
    public String stuName;
    public double stuWeight;

    // 构造函数可以进行重载
    // 如果没有定义构造方法，Java会默认给定一个构造方法，什么都不会做
    // 一旦定义清楚构造方法，Java就不会在提供任何构造方法
    public Student(){
        // 调用this，直接调用有参构造函数
        this(202405, "邱俊航", 93);
    }

    public Student(int stuID, String stuName, int stuWeight){
        // this只能在成员方法中使用
        // this的使用有三种方式
        // this获取当前对象的属性变量
        // this调用当前对象的方法
        // this() 调用构造函数 需要注意的是
        // this() 必须处于构造方法代码的第一行

        this.stuID = stuID;
        this.stuName = stuName;
        this.stuWeight = stuWeight;
    }
}
