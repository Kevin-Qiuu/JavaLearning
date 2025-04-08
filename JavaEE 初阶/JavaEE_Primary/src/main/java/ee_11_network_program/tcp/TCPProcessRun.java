package ee_11_network_program.tcp;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/*
    这个类实现后应用失败，原因是 Java 源码的限制
    ThreadPoolExecutor 线程池只能接收元素为 Runnable 的阻塞队列，
    然后在调用 summit 方法后会通过 newTaskFor 方法将其封装为 FutureTask<Void> 类
    而 FutureTask<null> 类没有实现 comparable 接口，我们也无法重新实现这个接口
    所以无法实现，所以在这个 TCP 连接中，就不用去设置阻塞队列了，线程不够了以后，立刻创建临时线程，不要进行阻塞
 */

public class TCPProcessRun extends FutureTask<Runnable> implements Comparable<FutureTask<Runnable>>{
    private long createTime;

    public TCPProcessRun(long createTime, Runnable runnable) {
        super(new Callable<Runnable>() {
            @Override
            public Runnable call() throws Exception {
                runnable.run();
                return null;
            }
        });
        this.createTime = createTime;
    }

    @Override
    public int compareTo(FutureTask<Runnable> o) {
        if (!(o instanceof TCPProcessRun tcpProcessRun)) {
            throw new IllegalArgumentException("传递参数不是 TCPProcessRun 或者其子类");
        }
        return (int) (this.getCreateTime() - tcpProcessRun.getCreateTime());
    }

    public long getCreateTime() {
        return createTime;
    }

}
