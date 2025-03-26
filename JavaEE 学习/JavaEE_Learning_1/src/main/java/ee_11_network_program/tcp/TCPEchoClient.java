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
//                只要 TCP 处于连接，数据流就一直存在，如果数据流中没有数据，操作系统会对调用读取输入流的进程进行操作系统级别的休眠
//                如果此时读完了一行，还没有下一行数据来，当前线程就会阻塞等待下一行数据的来
//                这样设计的目的：
//                  1. 客户端等待服务器的响应，服务器响应完毕后自然会发送响应数据；
//                  2. 服务器等待客户端的请求，客户端产生请求后也自然会发送请求数据。
//                所以如果循环处理任务的条件为 hasNextLine()，有数据时会返回 true，只有在连接中断后返回 fasle，
//                而处于连接但是没有数据时会阻塞等待下一行数据的到来，如果下一行数据迟迟不来，就会出现程序阻塞 while 语句的条件无法跳出的情况

//                同样，由于是等待系统资源的阻塞，JVM 仍然无法识别所以在这里线程的状态还是 RUNNABLE
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
