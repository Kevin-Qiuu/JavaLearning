package ee_11_network_program.udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPEchoClient {
    private final DatagramSocket socket;  // 创建一个 socket 来管理客户端
    private final String serverIP;
    private final int serverPort;

    public UDPEchoClient(String serverIP, int serverPort) throws SocketException {
        socket = new DatagramSocket(); // 不指定端口号，由操作系统来进行分配端口号
        // 设置服务器端的 IP 和端口号
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public void start() throws IOException {
        System.out.println("============================================");
        System.out.println("====  The UDP Client has been started.  ====");
        System.out.println("============================================");

        while (true){
            Scanner scanner = new Scanner(System.in);
            // 1. 接收客户端用户的输入
            System.out.print("Input your message: ");
            String request = scanner.nextLine();
            // 2. 封装客户端的网络地址（IP 和端口号）
            // 通过 InetSocketAddress 封装服务器端的网络地址，使用 SocketAddress 引用
            SocketAddress serverAddress = new InetSocketAddress(serverIP, serverPort);
            // 3. 打包客户端的请求数据
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(StandardCharsets.UTF_8),
                    request.length(), serverAddress);
            // 4. 向服务器发送打包好的请求数据
            socket.send(requestPacket);
            // 5. 接收服务器端的响应数据
            // 创建DatagramPacket来封装接受的数据是
            DatagramPacket responsePacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(responsePacket);
            // 6. 解析响应数据并处理
            String response = new String(responsePacket.getData(), 0,
                    responsePacket.getLength(), StandardCharsets.UTF_8);
            process(request, response);
        }
    }

    private void process(String request,String response) {
        System.out.printf("request: %s, response: %s\n", request, response);
    }

    public static void main(String[] args) throws IOException {
        UDPEchoClient client_1 = new UDPEchoClient("127.0.0.1", 6868);
        client_1.start();
    }
}
