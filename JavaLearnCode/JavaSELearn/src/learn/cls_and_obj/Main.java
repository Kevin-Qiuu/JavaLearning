package learn.cls_and_obj;

import java.util.Scanner;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void print_line(int num, int target_i){
        for (int i = 0; i < num; ++i){
            if (i == target_i || i == num - target_i - 1)
                System.out.print('*');
            else
                System.out.print(' ');
        }
        System.out.println();
    }


    public static void print_x(int num){
        if (num % 2 == 0) {
            for (int i = 0; i < num / 2; i++) {
                print_line(num, i);
            }
            for (int i = num / 2 - 1; i >= 0; --i){
                print_line(num, i);
            }
        } else {
            // 奇数的情况
            for (int i = 0; i <= num / 2; i++) {
                print_line(num, i);
            }
            for (int i = num / 2 - 1; i >= 0; --i){
                print_line(num, i);
            }
        }
    }

    public static Boolean logInPasswd(String correct_passwd, String user_passwd, Scanner in){
        int log_chance_num = 3;
        while (log_chance_num > 1){
            if (correct_passwd.equals(user_passwd))
                return true;
            else {
                log_chance_num--;
                System.out.print("密码错误，重新输入 > ");
                user_passwd = in.nextLine();
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        print_line(5, 2);
//        print_x(5);
        System.out.print("欢迎光临，请输入密码 > ");
        Scanner in = new Scanner(System.in);
        String user_passwd = in.nextLine();
        String correct_passwd = "QJHCJH";
        boolean is_correct = logInPasswd(correct_passwd, user_passwd, in);
        if (is_correct) {
            System.out.println("密码正确，登录成功");
            System.out.println("...");
        } else {
            System.out.println("连续三次输入密码错误，退出系统");
            System.exit(0);
        }

    }
    public static void main9(String[] args) {
//        water_flower(1000);
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()){
            int a = in.nextInt();
            System.out.println(a);
        }
    }

    public static void water_flower(int num){
        for (int i = 0; i <= num; ++i){
            int temp = i;
            int ret = 0;
            while (temp != 0){
                int aa = temp % 10;
                ret += (int) Math.pow(aa, 3);
                temp /= 10;
            }
            if (i == ret)
                System.out.println(i);
        }
    }

    public static Boolean is_prime(int num){
        if (num <= 2)
            return true;
        for (int i = 2; i < num; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    public static void main8(String[] args) {
        // 判断一个数是不是素数
        boolean ret;
        ret = is_prime(13);
        System.out.println(ret);
    }
    public static void main7(String[] args) {
        // 统计数字 9 出现的次数
        int num = 0;
        for (int i = 1; i < 101; i++) {
            int temp = i;
            while (temp != 0){
                int j = temp % 10;
                if (j == 9)
                    ++num;
                temp /= 10;
            }
        }
        System.out.println("1-100的数字中一共出现了 "+num+" 次数字 9");
    }



    public static void main6(String[] args) {
        // 短路运算

    }

    public static void main5(String[] args) {
        // 调用Java运行代码，在命令行后面添加字符串，进而为main函数的args数组赋值
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    public static void main4(String[] args) {
        // 首先进行float的变量赋值操作，与其他语言不同，要在数值的末尾添加f
        float a = 123;
        float b = 456f;
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }


    public static void main3(String[] args) {
        char a = '我';
        System.out.println(a);
        // ====== 打印各种类型的最大值和最小值（使用包装类的属性进行打印）====
        System.out.println("=====Char的最大值和最小值打印=====");
        System.out.println(Character.MAX_VALUE);
        System.out.println(Character.MIN_VALUE);
        System.out.println("=====Int的最大值和最小值打印=====");
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println("=====Double的最大值和最小值打印=====");
        System.out.println(Double.MAX_VALUE);
        System.out.println(Double.MIN_VALUE);
    }

    public static void main2(String[] args) {
        int a = 10;
        String b = String.valueOf(a);
        System.out.println("a="+a); // 字符串拼接

        // 参考之前C++的学习经历，这里应该是进行了运算符重载，实际上应该是重新调用了一个函数
        // 函数的实现思想应该是首先将"a=="传递给函数的一个形参，然后将a进行类型转换，转换成字符类型
        // 也就是调用了String的valueOf函数，完成整个字符串拼接。
        System.out.println("b="+b);
    }

    public static void main1(String[] args) {
        //TIP 当文本光标位于高亮显示的文本处时按 <shortcut actionId="ShowIntentionActions"/>
        // 查看 IntelliJ IDEA 建议如何修正。
        System.out.println("Hello and welcome!");

        System.out.println("Hello world");

        for (int i = 1; i <= 5; i++) {
            //TIP 按 <shortcut actionId="Debug"/> 开始调试代码。我们已经设置了一个 <icon src="AllIcons.Debugger.Db_set_breakpoint"/> 断点
            // 但您始终可以通过按 <shortcut actionId="ToggleLineBreakpoint"/> 添加更多断点。
            System.out.println("i = " + i);
        }
    }
}