package ee_09_thread_safe_class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_02_Collections_synchronizedList {
    public static void main(String[] args) {
        // Collections.synchronizedList 是一个把 List 中所有会出现读写操作的方法都加了 synchronized 的类
        // 使用的锁对象是 synchronizedList 的一个成员变量
        List<Integer> list = Collections.synchronizedList(new ArrayList<>(10));
        for (int i = 0; i < 10; i++) {
            int finalI = i + 1;
            Thread thread = new Thread(() -> {
                list.add(finalI);
                System.out.println(list);
            });
            thread.start();
        }
    }
}
