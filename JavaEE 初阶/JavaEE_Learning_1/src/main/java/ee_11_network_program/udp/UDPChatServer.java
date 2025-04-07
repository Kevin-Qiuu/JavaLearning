package ee_11_network_program.udp;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

public class UDPChatServer extends UDPEchoServer {

    private HashMap<String, String> dict;

    /**
     * @param port 服务器所要使用的端口号
     * @throws SocketException
     */
    public UDPChatServer(int port) throws SocketException {
        super(port);
        dict = new HashMap<>();
        dict.put("apple", "fruit");
        dict.put("dog", "animal");
        dict.put("coffee", "drinks");
        dict.put("nabati", "snack");
    }

    @Override
    protected String process(String request) {
        String dict_value = dict.get(request);
        return dict_value == null ? "This world is not exited in dict." : dict_value;
    }

    public static void main(String[] args) throws IOException {
        UDPChatServer chatServer = new UDPChatServer(6868);
        chatServer.start();
    }
}
