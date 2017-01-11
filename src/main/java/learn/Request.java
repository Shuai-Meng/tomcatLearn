package learn;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by m on 17-1-7.
 */
public class Request {
    private InputStream inputStream;
    private String uri;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getUri() {
        return this.uri;
    }

    public void parse() {
        StringBuffer sb = new StringBuffer(2048);
        byte[] buffer = new byte[2048];
        int i;

        try {
            i = inputStream.read(buffer);
        } catch(IOException e) {
            i = -1;
            e.printStackTrace();
        }

        for(int j = 0; j < i; j++)
            sb.append((char) buffer[j]);

        String request = sb.toString();
        int firstSpace = request.indexOf(" ");
        if(firstSpace > -1) {
            int secondSpace = request.indexOf(" ", firstSpace + 1);
            if(secondSpace > firstSpace) {
                uri = request.substring(firstSpace + 1, secondSpace);
                return;
            }
        }
        uri = null;
    }
}
