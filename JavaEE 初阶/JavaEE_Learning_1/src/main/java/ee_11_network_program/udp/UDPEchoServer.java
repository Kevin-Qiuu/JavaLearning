package ee_11_network_program.udp;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPEchoServer {
    // 创建一个 socket 实例，作为处理网络协议，提供 UDP 服务的对象
    private final DatagramSocket socket;

    // 服务器需要指定端口，以便客户端找到服务器

    /**
     *
     * @param port 服务器所要使用的端口号
     * @throws SocketException
     */
    public UDPEchoServer(int port) throws SocketException {
        // 指定服务器的端口不可以是知名端口(0～1024），也不可以超出最大端口号 65535，需要做程序判断
        if (port < 1024 || port > 65535) {
            throw new BindException("The port should be from 1024 to 65535");
        }
        socket = new DatagramSocket(port);
    }

    // 指定服务器的服务开启方法
    public void start() throws IOException {
        System.out.println("============================================");
        System.out.println("====  The UDP Server has been started.  ====");
        System.out.println("============================================");



        // 开始进行服务器业务
        while (true){
            // 1. 创建一个 Packet 实例来管理用户发来的请求数据
            // 以下表示可以接收的数据长度为 1024 个字节，1kb
            DatagramPacket requestPacket = new DatagramPacket(new byte[1024], 1024);
            // 2. 调用服务器的 socket 来接收数据
            // 这里的 requestPacket 是输出型参数，如果没有客户端请求，则会阻塞等待
            // socket.receive() 方法也会把 requestPacket 的 IP 和端口号进行赋值，以保存
            socket.receive(requestPacket);
//            System.out.println(socket.getLocalAddress());
//            System.out.println(socket.getLocalPort());
            // 3. 解析接收到的数据，采用应用层协议（UTF-8 编码）
            String request = new String(requestPacket.getData(), 0,
                    requestPacket.getLength(), StandardCharsets.UTF_8);
            // 4. 调用服务器方法处理用户请求
            String response = process(request);
            // 5. 封装响应的数据成 DatagramPacket，调用
            // 调用 requestPacket.getSocketAddress() 获取客户端的 IP 和端口号来封装响应数据
            // setSocketAddress 方法向下调整，调用 InetSocketAddress 的 set 方法
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(StandardCharsets.UTF_8),
                    response.length(), requestPacket.getSocketAddress());
            // 6. 向客户端发送封装的响应数据,
            // 发送的 DatagramPacket 要封装有发送的数据和发送目标方的 IP 和端口号
            // 以此 send 方法才可以顺利发送
            socket.send(responsePacket);
            // 7. 打印日志
            // InetAddress 存放的是 IP 信息，所以 requestPacket.getAddress() 获取的是 IP
            // port 存放的是端口号
            System.out.printf("[%s:%d] request = %s, response = %s\n",
                    requestPacket.getAddress().toString(), requestPacket.getPort(), request, response);
        }
    }

    // protected 在继承的类和类内中是可以获取的
    protected String process(String request) {
        // 回显服务器
        return "Server echo : " + request;
    }

    public static void main(String[] args) throws IOException {
        UDPEchoServer server_1 = new UDPEchoServer(8888);
        server_1.start();
    }

}
