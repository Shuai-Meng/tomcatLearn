package learn;

import java.io.*;
import java.net.Socket;

/**
 * Created by m on 16-12-25.
 */
public class Client {
    private Socket socket = null;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void sendRequest() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET /index.jsp HTTP/1.1");
            out.println("Host: localhost:8080");
            out.println("Connection: Close");
            out.println();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleResponse() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean loop = true;
            StringBuffer sb = new StringBuffer(8096);

            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }

            System.out.println(sb.toString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
