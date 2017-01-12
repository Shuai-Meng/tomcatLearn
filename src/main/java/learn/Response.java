package learn;

import java.io.*;
import java.util.Locale;
import javax.servlet.*;

/**
 * Created by m on 17-1-7.
 */
public class Response implements ServletResponse {
    private Request request;
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendResponse() throws IOException {
        try {
            File file = new File(Constants.WEB_ROOT, request.getUri());
            String response = "";

            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String s;
                while ((s = br.readLine()) != null)
                    response += s + "\n";

                br.close();
            } else {
                response = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
            }
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCharacterEncoding() {
        return null;
    }

    public String getContentType() {
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        PrintWriter printWriter = new PrintWriter(outputStream, true);
        return printWriter;
    }

    public void setCharacterEncoding(String charset) {

    }

    public void setContentLength(int len) {

    }

    public void setContentType(String type) {

    }

    public void setBufferSize(int size) {

    }

    public int getBufferSize() {
        return 0;
    }

    public void flushBuffer() throws IOException {

    }

    public void resetBuffer() {

    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {

    }

    public void setLocale(Locale loc) {

    }

    public Locale getLocale() {
        return null;
    }
}
