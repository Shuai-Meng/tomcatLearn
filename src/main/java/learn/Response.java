package learn;

import java.io.*;
import java.net.Socket;

/**
 * Created by m on 17-1-7.
 */
public class Response {
    private Request request;
    private OutputStream outputStream;
    private static final String WEB_ROOT = "/home/m/backup";

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendResponse() throws IOException {
        try {
            File file = new File(WEB_ROOT, request.getUri());
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                PrintWriter pr = new PrintWriter(outputStream);

                String s;
                while ((s = br.readLine()) != null)
                    pr.println(s);

                br.close();
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                outputStream.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
