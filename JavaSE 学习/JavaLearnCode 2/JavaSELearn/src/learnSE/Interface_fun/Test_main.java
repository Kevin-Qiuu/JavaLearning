package learnSE.Interface_fun;

import java.util.Arrays;

public class Test_main {
    public static void print_stu_com_ret(int ret){
        if (ret > 0){
            System.out.println("student1 > student2");
        } else if (ret == 0){
            System.out.println("student1 = student2");
        } else {
            System.out.println("student1 < student2");
        }
    }

    public static void BubbleSort(Student[] students) throws CloneNotSupportedException {
        PaperNumComparable paperNumComparable = new PaperNumComparable();
        // 使用自行编写的比较器进行排序，这里使用的是发文章的数量
        for (int i = 0; i < students.length; i++) {
            for (int j = 0; j < students.length - i - 1; j++) {
                int ret = paperNumComparable.compare(students[j], students[j + 1]);
                if (ret > 0){
                    Student tmp = (Student)students[j].clone(); // 这里要使用克隆接口
                    students[j] = students[j + 1];
                    students[j + 1] = tmp;
                    // 调用到这里以后，student[j]原先引用的空间没有变量引用
                    // Java 垃圾回收机制会自动回收变量空间
                }
            }
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        // 使用 BubbleSort 对已创建的 Students 数组进行排序
        Student[] students = new Student[3];
        students[0] = new Student("Xiaowang",20,2,5);
        students[1] = new Student("Xiaohong",18,4,4);
        students[2] = new Student("Xiaolang",19,3,4);
        for(Student x : students){
            System.out.println(x);
        }
        System.out.println("===========================");
        BubbleSort(students);
        for(Student x : students){
            System.out.println(x);
        }
    }

    public static void main4(String[] args) throws CloneNotSupportedException {
        Student stu = new Student("Xiaozhang",17,3,4);
        Student stu_clone = (Student)stu.clone();
        stu.arr[0] = 99;
        System.out.println(stu);
        System.out.println(stu_clone);
    }
    public static void main3(String[] args) {
        Student[] students = new Student[3];
        students[0] = new Student("Xiaowang",20,2,5);
        students[1] = new Student("Xiaohong",18,4,4);
        students[2] = new Student("Xiaolang",19,3,4);
        // 会调用 Comparable 接口中已经重写的 compareTo 方法进行数组的排序
        Arrays.sort(students);
        for(Student x : students){
            System.out.println(x);
        }
    }
    public static void main2(String[] args) {
        Student stu1 = new Student("Xiaoqiu",20,2,5);
        Student stu2 = new Student("Xiaohong",22,3,3);
        System.out.println("使用默认比较接口进行比较（Comparable）：");
        int ret = stu1.compareTo(stu2);
        print_stu_com_ret(ret);
        // 如果项目后期新增一个功能，要使用学生的姓名作为比较根据，不根据原先所说的年龄
        // 如果直接改动项目原先的 to_compare 方法，会有导致整个项目崩溃的可能
        System.out.println("根据学生的名字进行比较（NameComparable）");
        // 为此，有另外一个解决方法是新创建一个比较器，对原先的代码进行补充,使用此接口以其他变量为依据进行比较
        // 因为会有多个变量的比较器，为了明确比较器的开发规范，定义了一个新的 Comparator 接口
        ret = new NameComparable().compare(stu1, stu2); // 采用匿名对象的方法进行调用
        print_stu_com_ret(ret);
        // 继续增加一个以学生的论文数量进行依据的比较器
        System.out.println("根据学生的发文数量进行比较（Papercomparable）");
        ret = new PaperNumComparable().compare(stu1, stu2); // 同样是采用匿名对象的方法进行调用
        print_stu_com_ret(ret);
        // 使用 cloneable
    }
}
