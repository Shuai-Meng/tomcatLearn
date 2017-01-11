package learn;

import java.io.IOException;
import java.net.*;

/**
 * Created by m on 17-1-7.
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean shutdown = false;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port, 10);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void await() {
        while(!shutdown) {
            try {
                socket = serverSocket.accept();
                Request request = new Request(socket.getInputStream());
                request.parse();

                Response response = new Response(socket.getOutputStream());
                response.setRequest(request);
                response.sendResponse();

                socket.close();
                shutdown = request.getUri().equals("SHUT_DOWN");
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
