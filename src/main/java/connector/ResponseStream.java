package connector;

import com.sun.deploy.net.*;
import util.StringManager;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by shuaimeng2 on 2017/2/14.
 */
public class ResponseStream extends OutputStream {
    private HttpResponse httpResponse;
    private OutputStream outputStream;
    protected static StringManager sm = StringManager.getManager(Constants.Package);
    /**
     * Has this stream been closed?
     */
    protected boolean closed = false;

    /**
     * Should we commit the response when we are flushed?
     */
    protected boolean commit = false;

    /**
     * The number of bytes which have already been written to this stream.
     */
    protected int count = 0;


    /**
     * The content length past which we will not write, or -1 if there is
     * no defined content length.
     */
    protected int length = -1;

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    public boolean getCommit() {
        return (this.commit);
    }

    public ResponseStream(HttpResponse httpResponse) {
        super();
        commit = false;
        this.httpResponse = httpResponse;
        try {
            this.outputStream = httpResponse.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int b) throws IOException {

        if (closed)
            throw new IOException(sm.getString("responseStream.write.closed"));

        if ((length > 0) && (count >= length))
            throw new IOException(sm.getString("responseStream.write.count"));

        write(b);
        count++;

    }

    public void write(byte b[], int off, int len) throws IOException {

        if (closed)
            throw new IOException(sm.getString("responseStream.write.closed"));

        int actual = len;
        if ((length > 0) && ((count + len) >= length))
            actual = length - count;
        write(b, off, actual);
        count += actual;
        if (actual < len)
            throw new IOException(sm.getString("responseStream.write.count"));

    }
}
