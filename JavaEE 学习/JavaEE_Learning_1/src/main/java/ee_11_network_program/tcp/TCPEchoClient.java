package ee_11_network_program.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TCPEchoClient {
    private final Socket socket;
    private final String serverIP;
    private final int serverPort;

    public TCPEchoClient(String serverIP, int serverPort) throws IOException {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        // 1. 根据服务器的 IP 和端口号连接网址
        socket = new Socket(serverIP, serverPort);  // 连接服务器
//        System.out.println("Connection successfully!");
    }

    public void start() throws IOException {
        System.out.println("============================================");
        System.out.println("====  The TCP Client has been started.  ====");
        System.out.println("============================================");
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            while (true) {
                // 2. 读取用户输入的请求数据
                Scanner scanner = new Scanner(System.in);
                System.out.print("Input your message: ");
                String request = scanner.nextLine();

                // 3. 读取网络连接的输入流
                Scanner scannerTCP = new Scanner(inputStream);

                if (request.equals("quit")) {
                    break;
                }
                // 3. 向连接流写入数据
                PrintWriter writer = new PrintWriter(outputStream);
                writer.println(request);
                // 4. 刷新缓存区，强制操作系统将数据写入网卡
                writer.flush();
                // 5. 从数据流中接收服务器的响应数据
//                while (scannerTCP.hasNextLine()) {
//                只要 TCP 处于连接，数据流就一直存在，数据流一直存在，操作系统会对调用读取输入流的进程进行操作系统级别的休眠
//                如果此时读完了一行，还没有下一行数据来，当前线程就会这色等待下一行数据的来
//                但我们应用层的协议任务换行表示信息发送结束，这样客户端与服务器端都在阻塞等待写入数据流数据的输入，但都输入不了，造成死锁
//                同样由于是等待系统资源的阻塞，JVM 仍然无法识别所以在这里线程的状态还是 RUNNABLE
//                所以需要把 while 去掉 ，读完一行则表示本次消息发送完毕，再继续进行下一次消息的发送
//                因此不可以关闭数据流，主要双方有一方关闭了数据流，连接就终止了，另一方继续向数据流写入数据就会造成报错
//                if (scannerTCP.hasNextLine()) {
                String response = scannerTCP.nextLine();
                // 6. 打印日志
                System.out.printf("request: %s --> response: %s\n", request, response);
//                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        TCPEchoClient tcpEchoClient = new TCPEchoClient("127.0.0.1", 6868);
        tcpEchoClient.start();
    }
}
