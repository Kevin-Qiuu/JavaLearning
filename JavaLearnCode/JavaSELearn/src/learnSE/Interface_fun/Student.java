package learnSE.Interface_fun;
// 使用 Java 官方提供的接口
// Comparable
// Comparator
// Cloneable （深 & 浅 拷贝）
// 抽象类和接口的区别

// 首先使用 Comparable 的官方接口

import java.util.Arrays;
 // Cloneable 是一个空接口，承接该接口表示当前类可以被克隆
public class Student implements Comparable<Student>, Cloneable{
    public String name;
    public int age;
    public int papers_num;
    public int[] arr;

    Student(String name, int age, int papers_num, int arr_num){
        this.name = name;
        this.age = age;
        this.papers_num = papers_num;
        this.arr = new int[arr_num];
        for (int i = 0; i < arr_num; i++) {
            arr[i] = i;
        }
    }

     @Override
     protected Object clone() throws CloneNotSupportedException {
        Student tmp = (Student)super.clone(); // 这里执行的是浅拷贝
         tmp.arr = new int[arr.length];
         for (int i = 0; i < arr.length; i++) {
             tmp.arr[i] = i;
         }
         return tmp;
     }

     @Override
    public int compareTo(Student stu) {
        // to_compare 方法是对学生年龄的比较
        return this.age - stu.age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", papers_num=" + papers_num +
                ", arr=" + Arrays.toString(arr) +
                '}';
    }
}
