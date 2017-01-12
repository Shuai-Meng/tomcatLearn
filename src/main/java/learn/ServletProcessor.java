package learn;

import javax.servlet.*;
import java.io.*;
import java.net.*;

/**
 * Created by shuaimeng2 on 2017/1/12.
 */
public class ServletProcessor {
    private URLClassLoader loader = null;

    private void createClassLoader() {
        try {
            URL[] urls = new URL[1];
            File classPath = new File(Constants.WEB_ROOT+"tomcatLearn\\target\\classes\\learn");
            String repository = (new URL("File", null, classPath.getCanonicalPath() +
                    File.separator)).toString();
            urls[0] = new URL(null, repository, (URLStreamHandler)null);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        System.out.println(loader.getURLs()+":"+servletName);
        createClassLoader();

        Class servletClass;
        try{

            servletClass = loader.loadClass(servletName);
            Servlet servlet = (Servlet)servletClass.newInstance();
            servlet.service(request, response);
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
