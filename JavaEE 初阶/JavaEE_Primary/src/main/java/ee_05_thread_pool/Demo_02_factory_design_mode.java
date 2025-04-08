package ee_05_thread_pool;

class HotPot{
    int potSize = 20;
    int potPrice = 10;
    String potName = "一宫格";
    String potType = "白水锅";

    /**
     * 以下编写构造方法编译器会报错，原因在于编译器在识别每一个方法时靠的是方法签名
     * 方法签名有方法名、形参类型（不包括形参名字）构成，所以方法签名重复，编译器报错
     * 但很多场景中需要单独使用特定的变量来创建对象，所以需要绕开 java 的语言限制
     * 为此提出了工厂设计模式
    public HotPot(int potSize, String potName){
        this.potSize = potSize;
        this.potName = potName;
    }

    public HotPot(int potPrice, String potType){
        this.potPrice = potPrice;
        this.hotType = potType;
    }
     **/

    // 工厂模式的构造方法，依靠类的静态方法
    public static HotPot CreatHotPotBySizeName(int potSize, String potName){
        HotPot hp = new HotPot();
        hp.potSize = potSize;
        hp.potName = potName;
        return hp;
    }

    public static HotPot CreateHotPotByPricePotType(int potPrice, String potType){
        HotPot hp = new HotPot();
        hp.potPrice = potPrice;
        hp.potType = potType;
        return hp;
    }

    public void printHotPot(){
        System.out.println("锅的尺寸："+ potSize
                         + " 锅的价格：" + potPrice
                         + " 锅的类型：" + potType
                         + " 锅的名字：" + potName);
    }

}

public class Demo_02_factory_design_mode {
    public static void main(String[] args) {
        HotPot hotPot1 = HotPot.CreateHotPotByPricePotType(13, "辣锅");
        HotPot hotPot2 = HotPot.CreatHotPotBySizeName(30, "鸳鸯锅");
        System.out.println("第一口火锅：hotPot1");
        hotPot1.printHotPot();
        System.out.println("第二口火锅：hotPot2");
        hotPot2.printHotPot();



    }

}
