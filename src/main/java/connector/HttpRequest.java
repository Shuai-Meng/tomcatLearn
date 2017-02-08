package connector;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.*;
import util.ParameterMap;
import util.RequestUtil;

/**
 * Created by shuaimeng2 on 2017/1/16.
 */
public class HttpRequest implements HttpServletRequest{
    private boolean parsed;
    private SocketInputStream socketInputStream;
    protected HashMap headers = new HashMap();//请求头
    protected ArrayList cookies = new ArrayList();
    protected ParameterMap parameters = null;//查询参数
    private int contentLength;
    private boolean requestedSessionURL;
    private boolean requestedSessionCookie;
    private String requestedSessionId;
    private String method;
    private String protocol;
    private String requestUri;
    private String queryString;

    public HttpRequest(SocketInputStream socketInputStream) {
        this.socketInputStream = socketInputStream;
    }

    public void addHeader(String name, Object value) {
        headers.put(name, value);
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public void setRequestedSessionCookie(boolean requestedSessionCookie) {
        this.requestedSessionCookie = requestedSessionCookie;
    }

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setRequestedSessionId(String requestedSessionId) {
        this.requestedSessionId = requestedSessionId;
    }

    public void setRequestedSessionURL(boolean requestedSessionURL) {
        this.requestedSessionURL = requestedSessionURL;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestURI(String requestURI) {
        this.requestUri = requestURI;
    }

    public String getAuthType() {
        return null;
    }

    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    public long getDateHeader(String name) {
        return 0;
    }

    public String getHeader(String name) {
        return (String) headers.get(name);
    }

    public Enumeration getHeaders(String name) {
        return null;
    }

    public Enumeration getHeaderNames() {
        return null;
    }

    public int getIntHeader(String name) {
        return 0;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPathInfo() {
        return null;
    }

    public String getPathTranslated() {
        return null;
    }

    public String getContextPath() {
        return null;
    }

    public String getQueryString() {
        return this.queryString;
    }

    public String getRemoteUser() {
        return null;
    }

    public boolean isUserInRole(String role) {
        return false;
    }

    public Principal getUserPrincipal() {
        return null;
    }

    public String getRequestedSessionId() {
        return this.requestedSessionId;
    }

    public String getRequestURI() {
        return this.requestUri;
    }

    public StringBuffer getRequestURL() {
        return null;
    }

    public String getServletPath() {
        return null;
    }

    public HttpSession getSession(boolean create) {
        return null;
    }

    public HttpSession getSession() {
        return null;
    }

    public boolean isRequestedSessionIdValid() {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    public Object getAttribute(String name) {
        return parameters.get(name);
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
        return this.contentLength;
    }

    public String getContentType() {
        return (String) headers.get("contentType");
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public String getParameter(String name) {
        parseParameter();
        return (String) parameters.get(name);
    }

    public Enumeration getParameterNames() {
        parseParameter();
        return null;
    }

    public String[] getParameterValues(String name) {
        parseParameter();
        return new String[0];
    }

    public Map getParameterMap() {
        parseParameter();
        return null;
    }

    public String getProtocol() {
        return this.protocol;
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

    private void parseParameter() {//TODO
        if (parsed)
            return;

        ParameterMap results = parameters;
        if (results == null)
            results = new ParameterMap();
        results.setLocked(false);

        String encoding = getCharacterEncoding();
        if (encoding == null)
            encoding = "ISO-8859-1";

        String queryString = getQueryString();
        try {
            RequestUtil.parseParameters(results, queryString, encoding);
        }
        catch (UnsupportedEncodingException e) {
            ;
        }

        String contentType = getContentType();
        if (contentType == null)
            contentType = "";
        int semicolon = contentType.indexOf(';');
        if (semicolon >= 0) {
            contentType = contentType.substring (0, semicolon).trim();
        } else {
            contentType = contentType.trim();
        }

        if ("POST".equals(getMethod()) && (getContentLength() > 0)
                && "application/x-www-form-urlencoded".equals(contentType)) {
            try {
                int max = getContentLength();
                int len = 0;
                byte buf[] = new byte[getContentLength()];
                ServletInputStream is = getInputStream();
                while (len < max) {
                    int next = is.read(buf, len, max - len);
                    if (next < 0 ) {
                        break;
                    }
                    len += next;
                }
                is.close();
                if (len < max) {
                    throw new RuntimeException("Content length mismatch");
                }
                RequestUtil.parseParameters(results, buf, encoding);
            } catch (UnsupportedEncodingException ue) {
                ;
            } catch (IOException e) {
                throw new RuntimeException("Content read fail");
            }
        }

        results.setLocked(true);
        parsed = true;
        parameters = results;
    }
}
