package learn;

import java.io.*;
import java.net.Socket;

/**
 * Created by m on 16-12-25.
 */
public class Client {
    private String host;
    private int port;
    private String uri;
    private Socket socket = null;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void sendRequest() {
        try {
            socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + uri + " HTTP/1.1");
            out.println("Host: " + host + ":" + port);
            out.println("Connection: Close");
            out.println();

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

            in.close();
            System.out.println(sb.toString());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
