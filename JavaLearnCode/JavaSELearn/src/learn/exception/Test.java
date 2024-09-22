package learn.exception;

class A{
    private int a;
    private int b;

    public A(int a, int b){
        this.a = a;
        this.b = b;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 这里的 CloneNotSupportedException 就是一个受查异常
        return super.clone();
    }
}

public class Test {
    // 尝试使用 受查异常
    // 受查异常是一个受编译器非常严格检查的异常，要么调用者直接处理，解决不了就给别人处理。
    // 受查异常 要么调用者自己捕获异常并解决 解决不了就再向外抛出
    // 否则不可以通过编译
    // 受查异常处理方式 1
    public static void exceptionResolve1() throws CloneNotSupportedException{
        A aa = new A(1,2);
        A bb = (A)aa.clone();
    }

    // 受查异常处理方式 2
    public static void exceptionResolve2(){
        A ac = new A(1,3);

        try{
            A ab = (A)ac.clone();
        } catch (CloneNotSupportedException e){
            System.out.println(e.getMessage()); // 打印异常信息
            e.printStackTrace(); // 打印调用栈
        }

    }

}
