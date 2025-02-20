package multi_thread;
import org.openjdk.jol.info.ClassLayout;


public class Demo11_Demo_Layout {
    static Demo11_Demo_Layout singleton = null;
    // 定义一些变量
    private int count;
    private long count1 = 200;
    private String hello = "";
    // 定义一个对象变量
    private TestLayout test001 = new TestLayout();

    public static void main(String[] args) throws InterruptedException {
        // 创建一个对象的实例
        Object obj = new Object();
        // 打印实例布局
        System.out.println("=== 任意Object对象布局，起初为无锁状态");
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        System.out.println("=== 延时4S开启偏向锁");
        // 延时4S开启偏向锁
        Thread.sleep(5000);
        // 创建本类的实例
        Demo11_Demo_Layout monitor = new Demo11_Demo_Layout();
        // 打印实例布局，注意查看锁状态为偏向锁
        System.out.println("=== 打印实例布局，注意查看锁状态为偏向锁");
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());

        System.out.println("==== synchronized加锁");
        // 加锁后观察加锁信息
        synchronized (monitor) {
            System.out.println("==== 第一层synchronized加锁后");
            System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
            // 锁重入，查看锁信息
            synchronized (monitor) {
                System.out.println("==== 第二层synchronized加锁后，锁重入");
                System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
            }
            // 释放里层的锁
            System.out.println("==== 释放内层锁后");
            System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        }
        // 释放所有锁之后
        System.out.println("==== 释放 所有锁");
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());

        System.out.println("==== 多个线程参与锁竞争，观察锁状态");
        Thread thread1 = new Thread(() -> {
            synchronized (monitor) {
                System.out.println("=== 在线程 A 中获取锁，参与锁竞争，当前只有线程 A 竞争锁，轻度锁竞争");
                System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
            }
        });
        thread1.start();

        Thread.sleep(100);
        Thread thread3 = new Thread(() -> {
            synchronized (monitor) {
                System.out.println("=== 在线程 C 中获取锁，参与锁竞争，当前只有线程 A 竞争锁，轻度锁竞争");
                System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
            }
        });
        thread3.start();

        // 休眠一会，不与线程 A 激烈竞争
        Thread.sleep(100);
        Thread thread2 = new Thread(() -> {
            synchronized (monitor) {
                System.out.println("=== 在线程 B 中获取锁，与其他线程进行锁竞争");
                System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
            }
        });
        thread2.start();

        // 不休眠直接竞争锁，产生激烈竞争
        System.out.println("==== 不休眠直接竞争锁，产生激烈竞争");
        synchronized (monitor) {
            // 加锁后的类对象
            System.out.println("==== 与线程 B 产生激烈的锁竞争，观察锁状态为 fat lock");
            System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        }
        // 休眠一会释放锁后
        Thread.sleep(1000);
        System.out.println("==== 释放锁后");
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());

        System.out.println("===========================================================================================");
        System.out.println("===========================================================================================");
        System.out.println("===========================================================================================");
        System.out.println("===========================================================================================");
        System.out.println("===========================================================================================");
        System.out.println("===========================================================================================");

        // 调用hashCode后才保存hashCode的值
        monitor.hashCode();
        // 调用hashCode后观察现象
        System.out.println("==== 调用hashCode后查看hashCode的值");
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        // 强制执行垃圾回收
        System.gc();
        // 观察GC计数
        System.out.println("==== 调用GC后查看age的值");
        System.out.println(ClassLayout.parseInstance(monitor).toPrintable());
        // 打印类布局，注意调用的方法不同
        System.out.println("==== 查看类布局");
        System.out.println(ClassLayout.parseClass(Demo11_Demo_Layout.class).toPrintable());
        // 打印类对象布局
        System.out.println("==== 查看类对象布局");
        System.out.println(ClassLayout.parseInstance(Demo11_Demo_Layout.class).toPrintable());



    }
}

class TestLayout {

}
