package connector;

import learn.ServletProcessor;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import org.apache.catalina.connector.http.

/**
 * Created by shuaimeng2 on 2017/1/16.
 */
public class HttpProcessor {
    private HttpRequest request;
    private HttpResponse response;
    private HttpRequestLine requestLine = new HttpRequestLine();

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

        int question = requestLine.indexOf("?");
        if (question >= 0) {
            request.setQueryString(new String(requestLine.uri, question + 1, requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }

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
            String rest = uri.substring(semicolon + match,length());
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

        String normalizedUri = normalize(uri);

        ((HttpRequest) request).setMethod(method);
        request.setProtocol(protocol);
        if (normalizedUri != null)
            ((HttpRequest) request).setRequestURI(normalizedUri);
        else
            ((HttpRequest) request).setRequestURI(uri);

        if (normalizedUri == null)
            throw new ServletException("Invalid URI: " + uri + "'");
    }

    private void parseHeaders(SocketInputStream socketInputStream) {
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
        request.addHeader(name, value);
    }

    private void normalize(String uri) {

    }
}
