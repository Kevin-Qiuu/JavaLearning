package ee_11_network_program.tcp;

import java.io.IOException;
import java.util.Scanner;

public class TCPMsgServer extends TCPEchoServer {

    public TCPMsgServer(int port) throws IOException {
        super(port);
    }

    @Override
    protected String process(String request) {
        System.out.println("Client: " + request);
        System.out.print("Input your message: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void main(String[] args) throws IOException {
        TCPMsgServer msgServer = new TCPMsgServer(6688);
        msgServer.start();
    }
}
