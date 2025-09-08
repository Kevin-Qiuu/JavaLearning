package learnSE.Interface_fun;

public class NameComparable implements Comparator<Student>{
    @Override
    public int compare(Student stu1, Student stu2) {
        // 调用 String 类的 comparable 接口中重写的 compareTo 方法
        return stu1.name.compareTo(stu2.name);
    }
}
