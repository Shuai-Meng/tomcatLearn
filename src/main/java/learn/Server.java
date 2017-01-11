package learn;

import java.io.IOException;
import java.net.*;

/**
 * Created by m on 17-1-7.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port, 10);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void await() {
        while(true) {
            try {
                socket = serverSocket.accept();
                Request request = new Request(socket.getInputStream());
                request.parse();

                if(request.getUri().equals("/SHUT_DOWN")) {
                    socket.close();
                    return;
                }
                else {
                    Response response = new Response(socket.getOutputStream());
                    response.setRequest(request);
                    response.sendResponse();

                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
