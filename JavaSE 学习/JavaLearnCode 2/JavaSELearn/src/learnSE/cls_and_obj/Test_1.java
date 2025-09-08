package learnSE.cls_and_obj;

public class Test_1 {

    // 下面的add方法构成了方法重载
    // 方法重载的要求如下：
    // 1、方法名相同
    // 2、方法的参数列表不同
    // 3、与方法的返回值类型无关
    //
    // java方法重载的底层逻辑就是方法签名
    // 虽然构成方法重载的方法名相同，但底层调用的是方法签名
    // 方法签名与方法名、方法参数列表有关，所以方法重载的要求也只与这二者相关，与方法的返回值类型无关
    //
    // 查看方法签名的步骤如下：
    // 代码经过编译之后，然后使用JDK自带的javap反汇编工具查看，具体操作：
    // 1. 先对工程进行编译生成.class字节码文件
    // 2. 在控制台中进入到要查看的.class所在的目录
    // 3. 输入：javap -v 字节码文件名字即可


    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static double add(double a, int b) {
        return a + b;
    }

    public static int com(int a, int b){
        int ret = Math.max(a, b);
        return ret;
    }

    public static int com(int a, int b, int c){
        int ret = Math.max(a, b);
        ret = Math.max(ret,c);
        return ret;
    }

    public static int add_num(int n){
        if (n == 1)
            return 1;
        return n + add_num(n - 1);
    }

    public static int sum_i_num(int n){
        if (n / 10 == 0)
            return n;
        return n % 10 + sum_i_num(n / 10);
    }

    public static int fib_naci(int n){
        if (n == 1 || n == 2)
            return 1;
        return fib_naci(n-1)+fib_naci(n-2);
    }

    // 可变参数编程



    public static void main(String[] args) {
        System.out.println(sum_i_num(1729));
        System.out.println(fib_naci(5));
    }

}
