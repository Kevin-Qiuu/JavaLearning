package learnSE.array;

import java.util.Arrays;

public class Array_learn {
    public static void main(String[] args) {
        // 不规则的二维数组
        // 因为二维数组的每一个元素都是一个数组的引用变量
        // 所以可以先定义出一个二维数组的行数，但不指定列数，等后期单独为其分配空间
        // 并为引用变量赋值

        // 下面构建一个不规则二维数组
        // 实际的数据结构我忘记什么名字了
        // 具体结构如下：
        /*
        [] ------> {1,2,3}
        [] ------> {1,2,3,4}
        [] ------> {1,2}
        [] ------> {1,2,3,5,6}
        [] ------> {1}
        */
        int[][] arr = new int[8][];
        // 可以理解为一个数组有 8 个元素
        // 元素类型是一个数组的引用变量
        // 通过一个 for 循环依次创建引用变量所指向的数组
        for (int i = 0; i < arr.length; ++i){
            arr[i] = new int[i+1];
            Arrays.fill(arr[i], i+1);
        }

        for (int[] ints : arr) {
            for (int j = 0; j < ints.length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println();
        }

    }
    public static void main8(String[] args) {
        // Java里面，二维数组是一个有一维数组组成数组
        // 每一个元素都是一维数组的引用
        // 二维数组可以省略列
        int[][] arr = {{1,2,3},{4,5,6}};
        System.out.println(arr);
        System.out.println(arr[0]);
        System.out.println(arr[0][1]);
        // 某一次的打印结果如下
        /*
         * [[I@36baf30c
         * [表示是一个数组，[I表示数组的元素类型是一维数组
         *
         * [I@7a81197d
         * [表示是一个数组，I表示数组的元素类型是整型
         *
         * 2
         */
    }
    public static void main7(String[] args) {
        // 计算一个数组的平均值
        int[] arr = {12,34,56,77,99};
        int sum = 0;
        for(int x : arr){
            sum += x;
        }
        System.out.println((sum * 1.0) / arr.length);
        // sum * 1.0 进行double强转，确保计算的结果为浮点数
    }
    public static String myToString(int[] arr){
        if (arr == null || arr.length == 0)
            return "[]";
        String ret = "[";
        for (int i = 0; i < arr.length; i++) {
            ret += String.valueOf(arr[i]);
            if(i < arr.length - 1)
                ret += ", ";
            else
                ret += "]";
        }
        return ret;
    }
    public static void main6(String[] args) {
        int[] arr = {12, 34, 99, 52};
        String str = myToString(arr);
        System.out.println(str);
    }
    public static void main5(String[] args) {
        // 数组名是一个引用变量
        // 所以说 int[] arr
        // arr是一个引用变量，指向的是一个数组，存储的是数组的首地址
        // 需要注意的是 arr 是一个真实存在的变量
        // 按照先前对引用的理解，arr实际是一个数组的标签
        int[] arr = new int[5];
        System.out.println(arr);
        // 在一次测试中，arr的输出值是 [I@36baf30c
        // 数值格式
        // [ 表示是一个数组
        // I 表示是整型
        // @ 表示分隔符
        // 36baf30c 表示数组首元素的地址
    }
    public static void main4(String[] args) {
        // JVM 的内存空间分配
        // 方法区
        // 程序计数器
        // 虚拟机栈 java运行的环境，使得java开发可以多终端运行
        // 开发者可以更多的关注JVM的运行逻辑，而对真正底层的操作系统淡化注意力
        // 本地方法栈 一般用来指向 C/C++代码的方法
        // 堆
        // 当一个对象没有引用变量指向时，对象会被JVM自动回收
    }
    public static void main2(String[] args) {
        int[] arr = {12,34,56,78,90};
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // 这种for循环的格式是将数组的每一个变量进行提取，提取到变量x中
        // 因此要求x的变量类型要与所遍历的数组元素变量类型保持一致
        // 我猜背后的原理应该是与迭代器有关
        // 增强 for
        for(int x : arr){
            System.out.print(x + " ");
        }
        System.out.println();

        // 将数组以字符串的形式打印
        String arr_str = Arrays.toString(arr);
        System.out.println(arr_str);
    }
    public static void main1(String[] args) {
        boolean[] arr = {false, true};
        boolean[] arr_1 = new boolean[5]; // new boolean [] 的中括号内表示要占用内存空间的数组变量个数
        int[] arr_2 = new int[5]; // java中默认对数组中个变量赋值为0
         System.out.println();

    }
}
