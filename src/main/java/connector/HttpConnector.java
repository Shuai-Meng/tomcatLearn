package connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by shuaimeng2 on 2017/1/16.
 */
public class HttpConnector implements Runnable {
    boolean stopped;
    private String scheme = "http";

    public String getScheme() {
        return this.scheme;
    }

    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!stopped) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            HttpProcessor httpProcessor = new HttpProcessor(this);
            httpProcessor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
