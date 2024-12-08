package learnSE.cls_and_obj;

public class Class_learn {
    public static void main(String[] args) {
        // Java中，引用的概念无处不在
        // Java中，类与对象有一个全新的理念
        Student stu_1 = new Student();
        // 在这行代码中，有一个细节需要理解
        // stu_1是main方法的局部变量，在方法中定义的局部变量统一是存储在栈中
        // new表示实例化一个对象，new实例化的对象存储在堆区
        // 所以由此可见stu_1是一个引用，指向的是堆区上实例化的空间
        System.out.println(stu_1);
        // 在Java中，引用的输出值都是有其特定的格式
        // Student@7a81197d
        // Student  表示引用的是一个Student类型的对象
        // @        表示分隔符
        // 7a81197d 表示堆区上存储空间的地址
        System.out.println(stu_1.stuID);
        System.out.println(stu_1.stuName);
        System.out.println(stu_1.stuWeight+"kg");
    }
}
