package core;

import javax.servlet.*;
import java.io.*;

/**
 * Created by shuaimeng2 on 2017/1/12.
 */
public class SimpleServlet implements Servlet{

    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello. Roses are red.");
        out.close();
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
