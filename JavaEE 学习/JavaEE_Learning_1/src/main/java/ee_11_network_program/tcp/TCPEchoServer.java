package ee_11_network_program.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

public class TCPEchoServer {
    /**
     * TCP 协议中，ServerSocket 是服务器的套接字， Socket 是服务器与客户端之间通讯的数据流
     * ServerSocket 管理 TCP 服务器端，Socket 管理服务器与客户端之间的通讯数据流
     */

    protected final ServerSocket socket;
    public TCPEchoServer(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("============================================");
        System.out.println("====  The TCP Server has been started.  ====");
        System.out.println("============================================");

        /*
          Java 中，线程的 RUNNABLE 状态分为三个情况：
          1. 正在执行任务
          2. 准备就绪即将执行任务（从阻塞状态恢复）
          3. 等待操作系统的资源（Java 并没有对这一个情况做状态的划分）

          在调用 accept 方法后， Java 进程会一直等待底层的连接情况返回（网卡、操作系统等），所以是在等待操作系统级别的资源，属于 RUNNABLE 状态，
          而之所以在阻塞等待，是操作系统调用了系统级别的休眠方法，属于操作系统内核行为，JVM 无法观测到阻塞状态，
          所以 JVM 统一认定是进程正在等待底层资源，属于 RUNNABLE，同时进程也在阻塞等待。
         */

         // 1. 获取客户端的连接数据流


        // 循环扫描，等待客户端的请求
        while (true) {
            Socket clientSocket = socket.accept();
            process_connection(clientSocket);
        }
    }

    protected void process_connection(Socket clientSocket) throws IOException {
        System.out.printf("[%s:%d] client is online.\n", clientSocket.getInetAddress(), clientSocket.getPort());
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            // 2. 读取连接的输入数据流的数据
            Scanner scanner = new Scanner(inputStream); // 用于读取客户端的输入流
//            while (scanner.hasNextLine()) {
            while (scanner.hasNextLine()) {
                // TCP 如果一直处于连接，在没有消息时会阻塞等待下一行消息的输入
                String request = scanner.nextLine();
                String response = process(request);
                // 3. 向连接的输出数据流写入数据
                PrintWriter writer = new PrintWriter(outputStream);
                writer.println(response);
                // 清空缓存区，强制要求操作系统将数据写入网卡
                writer.flush();
                // 4. 打印日志
                System.out.printf("[%s:%d] request: %s --> response: %s\n",
                        clientSocket.getInetAddress(), clientSocket.getPort(), request, response);
            }
            System.out.printf("[%s:%d] client is offline.\n", clientSocket.getInetAddress(), clientSocket.getPort());
        }
    }

    protected String process(String request) {
        return request; // 回显服务器
    }

    public static void main(String[] args) throws IOException {
        TCPEchoServer tcpEchoServer = new TCPEchoServer(8888);
        tcpEchoServer.start();
    }
}
