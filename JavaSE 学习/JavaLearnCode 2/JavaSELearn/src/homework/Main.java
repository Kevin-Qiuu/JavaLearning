package homework;

import java.util.Scanner;

class Data {

    private int x;
    private int y;

    public Data(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}


class NumCom{
    public static int com_num(int x, int y){
        return Math.max(x, y);
    }
    public static double com_num(double a, double b, double c){
        double max = Math.max(a, b);
        max = Math.max(max, c);
        return max;
    }
}

public class Main {

    private static int han_num;
    public static void hanoi(int num, String beg, String mid, String tar){

        if (num == 1) {
            System.out.println(beg + " -> " + tar);
            han_num++;
            return;
        }
        hanoi(num - 1, beg, tar, mid);
        System.out.println(beg + " -> " + tar);
        han_num++;
        hanoi(num - 1, mid, beg, tar);
    }

    public static void main(String[] args) {
        hanoi(1, "a", "b", "c");
        System.out.println("一共需要 " + han_num + " 步");

        han_num = 0;
        hanoi(2, "a", "b", "c");
        System.out.println("一共需要 " + han_num + " 步");

        han_num = 0;
        hanoi(3, "a", "b", "c");
        System.out.println("一共需要 " + han_num + " 步");

        han_num = 0;
        hanoi(4, "a", "b", "c");
        System.out.println("一共需要 " + han_num + " 步");

    }



    public static int num_sum_r(int num){
        if (num == 1)
            return 1;
        return num + num_sum_r(num - 1);
    }

    public static int n_fac_sum(int n){
        int ret = 0;
        while(n > 0){
            ret += n_fac(n);
            --n;
        }
        return ret;
    }

    public static int n_fac(int n){
        if (n <= 2)
            return n;
        return n * n_fac(n - 1);
    }

    public static int fib_r(int n){
        if (n <= 2)
            return 1;
        return fib_r(n - 2) + fib_r(n - 1);
    }

    public static int num_sum(int num){
        if (num == 0)
            return 0;
        return num % 10 + num_sum(num / 10);
    }

    public static void print_num_r(int num){
        if (num == 0)
            return;
        print_num_r(num / 10);
        System.out.print(num % 10 + " ");
    }

    public static void main3(String[] args) {
//        print_num_r(1234);
//        System.out.println(num_sum(123));
//        System.out.println(fib_r(6));
//        System.out.println(n_fac(4));
//        System.out.println(num_sum_r(4));
//        System.out.println(NumCom.com_num(12, 10));
//        System.out.println(NumCom.com_num(10.5,2.3,12.6));
        System.out.println(n_fac_sum(3));

    }

    public static void main2(String[] args) {
        /*
            接口当中的成员变量默认是：public static final
            接口当中的成员方法默认是：public abstract
        */
    }
    public static void main1(String[] args) {
        /*
            1.super.data访问父类中的属性
            2.super.func() 访问父类的方法
            3.super() 访问父类的构造方法
        */
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Data data = new Data(x, y);
            System.out.println(data.getX() + data.getY());
        }
    }



}