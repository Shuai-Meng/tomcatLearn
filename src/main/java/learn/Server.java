package learn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shuaimeng2 on 2017/1/4.
 */
public class Server {
    private ServerSocket serverSocket = null;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port, 10);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void await() {
        try {
            Socket socket = serverSocket.accept();
            handle(socket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

    private void handle(Socket socket) throws IOException {

    }
}
