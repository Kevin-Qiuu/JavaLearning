package ee_01_multi_thread;

// 尝试通过 jconsole 跟踪多线程任务

// 日常开发中常见的几种通过内部类创建线程的方式：
// 通过内部类创建线程的方式只适用于在方法本体中使用，即临时需要创建线程，并没有单独抽象化线程 PCB
// 1. 使用 Thread 匿名内部类创建线程
// 2. 使用 Runnable 匿名内部类创建线程
// 3. 使用 Lambda 表达式创建线程

// 第三种方式：使用 Lambda 表达式创建线程
// 因为 Runnable 接口只有一个抽象方法，所以是一个函数式接口，在实现这个接口时可以使用 Lambda 表达式
// 使用 Lambda 表达式要满足以下：
// 核心要点：
// 1. 接口必须是函数式接口（只有一个抽象方法）。
// 2. 抽象方法可以有参数，也可以没有参数。
// 3. 抽象方法可以有返回值，也可以没有返回值。
// 4. 如果接口有多个默认方法或静态方法，不影响它是函数式接口，因为这些方法不是抽象方法。

// 对于 Lambda 表达式我的个人理解：
// () -> {}
// Lambda 表达式的主要目的是为了简化匿名内部类的实现，特别是那些只需要实现一个方法的接口（即函数式接口）。Lambda 提供了更加简洁和可读的方式来实现接口。
// Java 底层实现中，会创建一个 Lambda$ 类，这个类实现了函数式接口。
// 编译器遇到 Lambda 表达式时，会自动将用户实现的方法映射到函数式接口的抽象方法。
// Lambda 表达式会根据上下文中的函数式接口的类型，自动匹配参数类型和返回值类型。

public class Demo05_create_thread {
    public static void main(String[] args) {
        Thread mythread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Hello, my little thread!");
            }
        });
        mythread.start();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello, my main thread!");
        }
    }
}
