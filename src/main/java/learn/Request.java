package learn;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shuaimeng2 on 2017/1/6.
 */
public class Request {
    private String uri;
    private InputStream inputStream;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getUri() {
        return this.uri;
    }

    public void parse() {
        byte[] buffer = new byte[2048];
        int i = 0;

        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            i = -1;
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer(2048);
        for(int j = 0; j < i; j++)
            sb.append(buffer[j]);

        String request = sb.toString();
        int first = request.indexOf(" ");
        if(first != -1) {
            int second = request.indexOf(" ", first);
            if(second > first) {
                uri = request.substring(first + 1, second);
                return;
            }
        }
        uri = null;
    }
}
