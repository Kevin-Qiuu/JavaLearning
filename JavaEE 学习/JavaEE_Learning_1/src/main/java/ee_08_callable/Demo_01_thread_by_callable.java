package ee_08_callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class Demo_01_thread_by_callable {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable callable_01 = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("callable_01: call()");
                throw new Exception("callable_01: exception");
            }
        };

        Callable callable_int = new Callable() {
            @Override
            public Object call() throws Exception {
                int ret = 0;
                TimeUnit.SECONDS.sleep(4);
                for (int i = 0; i < 100; i++) {
                    ++ret;
                }
                return Integer.valueOf(ret);
            }
        };

        FutureTask<Object> futureTask = new FutureTask(callable_01);
        Thread thread_01 = new Thread(futureTask);
        thread_01.start();
        try{
            futureTask.get();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("捕获到线程 1 执行任务引发的异常");
        }

        futureTask = new FutureTask<>(callable_int);
        Thread thread_02 = new Thread(futureTask);
        thread_02.start();
        // 获取 callable 的 call 方法的返回值
        System.out.println(futureTask.get());
        // 因为 Callable 在执行任务时，可以抛出线程，故 get 方法要引入异常
        // 因为要获取执行任务的值，所以要等待线程执行完毕才能获取到，所以在等待的这个过程，线程进入阻塞状态。
    }
}
