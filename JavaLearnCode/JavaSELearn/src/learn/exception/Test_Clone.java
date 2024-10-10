package learn.exception;

// 尝试 Cloneable 接口的学习

// 对接 cloneable 表示当前类可以被克隆
class CloneTest implements Cloneable{
    private int[] arr;
    private int b;

    public CloneTest(int a, int b){
        this.arr = new int [a];
        this.b = b;
    }

    public int getArrAt(int index) {
        return arr[index];
    }

    public void setArrAt(int index, int num) {
        this.arr[index] = num;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CloneTest ret =  (CloneTest) super.clone();
        ret.arr = new int [arr.length];
        System.arraycopy(arr, 0, ret.arr, 0, arr.length);
        return ret;
    }

}

public class Test_Clone{


    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest cloneTest = new CloneTest(5, 5);
        CloneTest cloneTest1 = (CloneTest)cloneTest.clone();

        // 注意深浅拷贝 当前是浅拷贝 数组只是做了引用变量值的拷贝
        System.out.println(cloneTest.getArrAt(0));
        System.out.println(cloneTest1.getArrAt(0));


        cloneTest.setArrAt(0, 5);

        System.out.println(cloneTest.getArrAt(0));
        System.out.println(cloneTest1.getArrAt(0));
    }

    public static void main1(String[] args) {
        CloneTest cloneTest = new CloneTest(5, 5);
        CloneTest cloneTest1 = null;
        // 会抛出受查异常 CloneNotSupportedException
        try{
           cloneTest1 = (CloneTest)cloneTest.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        // 注意深浅拷贝 当前是浅拷贝 数组只是做了引用变量值的拷贝
        System.out.println(cloneTest.getArrAt(0));
        System.out.println(cloneTest1.getArrAt(0));


        cloneTest.setArrAt(0, 5);

        System.out.println(cloneTest.getArrAt(0));
        System.out.println(cloneTest1.getArrAt(0));
    }

}
