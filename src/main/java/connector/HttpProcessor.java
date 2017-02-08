package connector;

import core.*;
import util.RequestUtil;
import util.StringManager;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.*;
import java.net.Socket;

/**
 * Created by shuaimeng2 on 2017/1/16.
 */
public class HttpProcessor {
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();
    StringManager sm = StringManager.getManager("connector");

    public void process(Socket socket) {
        SocketInputStream socketInputStream = null;
        OutputStream outputStream = null;

        try {
            socketInputStream = new SocketInputStream(socket.getInputStream(), 2048);
            outputStream = socket.getOutputStream();

            request = new HttpRequest(socketInputStream);
            parseRequest(socketInputStream, outputStream);
            parseHeaders(socketInputStream);

            response = new HttpResponse(outputStream);
            response.setRequest(request);
            response.setHeader("Server", "Pyrmont Servlet Container");

            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }

    private void parseRequest(SocketInputStream socketInputStream, OutputStream outputStream) throws IOException, ServletException {
        socketInputStream.readRequestLine(requestLine);

        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        if (method.length () < 1) {
            throw new ServletException("Missing HTTP request method");
        } else if (requestLine.uriEnd < 1) {
            throw new ServletException("Missing HTTP request URI");
        }

        // Parse any query parameters out of the request URI
        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1, requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }

        // Checking for an absolute URI (with the HTTP protocol)
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");

            if (pos != -1) {
                pos = uri.indexOf('/', pos + 3);
                if (pos == -1)
                    uri = "";
                else
                    uri = uri.substring(pos);
            }
        }

        String match = ";jsessionid=";
        int semicolon = uri.indexOf(match);
        if (semicolon >= 0) {
            String rest = uri.substring(semicolon + match.length());
            int semicolon2 = rest.indexOf(';');
            if (semicolon2 >= 0) {
                request.setRequestedSessionId(rest.substring(0, semicolon2));
                rest = rest.substring(semicolon2);
            } else {
                request.setRequestedSessionId(rest);
                rest = "";
            }
            request.setRequestedSessionURL(true);
            uri = uri.substring(0, semicolon) + rest;
        } else {
            request.setRequestedSessionId(null);
            request.setRequestedSessionURL(false);
        }

        request.setMethod(method);
        request.setProtocol(protocol);
        String normalizedUri = normalize(uri);
        if (normalizedUri != null)
            request.setRequestURI(normalizedUri);
        else {
            request.setRequestURI(uri);
            throw new ServletException("Invalid URI: '" + uri + "'");
        }
    }

    private void parseHeaders(SocketInputStream socketInputStream) throws IOException, ServletException {
        while (true) {
            HttpHeader header = new HttpHeader();
            socketInputStream.readHeader(header);

            if (header.nameEnd == 0) {
                if (header.valueEnd == 0) {
                    return;
                } else {
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.colon"));
                }
            }
            String name = new String(header.name, 0, header.nameEnd);
            String value = new String(header.value, 0, header.valueEnd);

            if (name.equals("cookie")) {
                if (header.equals(DefaultHeaders.COOKIE_NAME)) {
                    Cookie cookies[] = RequestUtil.parseCookieHeader(value);
                    for (int i = 0; i < cookies.length; i++) {
                        if (cookies[i].getName().equals("jsessionid")) {
                            if (!request.isRequestedSessionIdFromCookie()) {
                                request.setRequestedSessionId(cookies[i].getValue());
                                request.setRequestedSessionCookie(true);
                                request.setRequestedSessionURL(false);
                            }
                        }
                        request.addCookie(cookies[i]);
                    }
                }
            } else if (name.equals("content-length")) {
                int n = -1;
                try {
                    n = Integer.parseInt(value);
                } catch (Exception e) {
                    throw new ServletException(sm.getString("httpProcessor.parseHeaders.contentLength"));
                }
                request.setContentLength(n);
            } else
                request.addHeader(name, value);
        }
    }

    private String normalize(String uri) {
        return uri;//TODO
    }
}
