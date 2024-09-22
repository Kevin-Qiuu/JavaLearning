package homework;

public class TestDemo{
	public static int cnt = 10;
    static {
        System.out.println("第一个构造代码块");
    }
    public TestDemo(){
        System.out.println("构造方法");
    }
    static {
        System.out.println("第二个构造代码块");
    }

    public static void main(String[] args) {
        System.out.println(cnt);
    }
}