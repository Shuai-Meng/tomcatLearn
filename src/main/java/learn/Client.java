package learn;

import java.io.*;
import java.net.Socket;

/**
 * Created by m on 16-12-25.
 */
public class Client {
    Socket socket = null;

    public void init() {
        try {
            socket = new Socket("10.100.33.165", 8080);

            //send 想想这个socket在网络层面上做了哪些事情
            boolean autoflush = true;
            PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
            //以下都是纯手工输出http协议本身
            out.println("GET /index.jsp HTTP/1.1");
            out.println("Host: localhost:8080");
            out.println("Connection: Close");
            out.println();

            //receive
            StringBuffer sb = new StringBuffer(8096);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean loop = true;
            while (loop) {
                if ( in.ready() ) {
                    int i=0;
                    while (i!=-1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
                Thread.currentThread().sleep(50);
            }

            System.out.println(sb.toString());
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        } catch (InterruptedException s) {
            s.printStackTrace();
        }
    }
}
