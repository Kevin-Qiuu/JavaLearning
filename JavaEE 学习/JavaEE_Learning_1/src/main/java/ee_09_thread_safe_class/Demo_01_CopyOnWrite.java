package ee_09_thread_safe_class;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class Demo_01_CopyOnWrite {

    public static void main1(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i + 1;
            Thread thread = new Thread(() -> {
                list.add(finalI);
                System.out.println(list);
            });
            thread.start();
        }

        TimeUnit.SECONDS.sleep(1);


        // 写时复制会出现的问题
        Iterator<Integer> itr = list.iterator();
        list.add(100000);
        Thread thread = new Thread(() -> {
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()){
                System.out.println("["+iterator.next()+"]");
            }
        });


        // 读
        for (int i = 0; i < list.size(); i++) {
            System.out.println("{"+list.get(i)+"}");
        }


        thread.start();
        while (itr.hasNext()){
            System.out.println(itr.next());
        }


    }
}
