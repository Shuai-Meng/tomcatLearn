package learn;

import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.*;

/**
 * Created by m on 17-1-7.
 */
public class Request implements ServletRequest {
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

    public Object getAttribute(String name) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

    }

    public int getContentLength() {
        return 0;
    }

    public String getContentType() {
        return null;
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String name) {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String name) {
        return new String[0];
    }

    public Map getParameterMap() {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public void setAttribute(String name, Object o) {

    }

    public void removeAttribute(String name) {

    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public String getRealPath(String path) {
        return null;
    }

    public int getRemotePort() {
        return 0;
    }

    public String getLocalName() {
        return null;
    }

    public String getLocalAddr() {
        return null;
    }

    public int getLocalPort() {
        return 0;
    }
}
