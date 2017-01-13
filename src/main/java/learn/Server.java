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
                Response response = new Response(socket.getOutputStream());
                response.setRequest(request);

                String uri = request.getUri();
                System.out.println(uri);
                if(uri.equals("/SHUT_DOWN")) {
                    socket.close();
                    return;
                } else if(uri.startsWith("/static")){
                    response.sendResponse();
                    socket.close();
                } else if(uri.startsWith("/servlet")) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    String responseStr = "HTTP/1.1 401 Forbidden\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: 18\r\n" +
                            "\r\n" +
                            "<h1>Forbidden</h1>";
                    socket.getOutputStream().write(responseStr.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
