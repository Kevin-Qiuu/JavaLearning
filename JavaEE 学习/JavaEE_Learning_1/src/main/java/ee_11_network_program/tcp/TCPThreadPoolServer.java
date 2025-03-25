package ee_11_network_program.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

public class TCPThreadPoolServer extends TCPEchoServer{

    private final ThreadPoolExecutor processThreadPool;

    public TCPThreadPoolServer(int port) throws IOException {
        super(port);
        processThreadPool = new ThreadPoolExecutor(3, 10, 1,
//                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1));
//                TimeUnit.SECONDS, new PriorityBlockingQueue<>(1));  // 没有实现的可能性，技术上受限了
                TimeUnit.SECONDS, new SynchronousQueue<>());  // SynchronousQueue 不存储任务，新任务直接交给空闲线程或新创建的线程执行
        // 只有阻塞队列队满时，才会启动临时线程去执行任务，在队列没有满时，会先使线程进入阻塞状态
    }

    @Override
    public void start() throws IOException {
        System.out.println("=============================================================");
        System.out.println("====  The TCP Server with thread pool has been started.  ====");
        System.out.println("=============================================================");

        while (true) {
            Socket clientSocket = socket.accept();
            // TCP 服务器有一个已完成连接队列（ACCEPT队列），
            // accept 每次执行后都会在队列中出队一个数据流，在队列为空时，accept 就会阻塞等待（操作系统级别的阻塞）
            // accept 方法仅响应新连接请求，与已有连接无关，
            processThreadPool.submit(new TCPProcessRun(System.currentTimeMillis(), ()->{
                try {
                    process_connection(clientSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        }
    }

    public static void main(String[] args) throws IOException {
        TCPThreadPoolServer tcpThreadPoolServer = new TCPThreadPoolServer(6868);
        tcpThreadPoolServer.start();
    }

}
