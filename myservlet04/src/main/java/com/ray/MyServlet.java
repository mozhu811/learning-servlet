package com.ray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-09 下午6:57
 **/
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream fis = new FileInputStream(new File("/Users/ray/IdeaProjects/LearningServlet/myservlet04/src/banana.png"));
        byte[] buf = new byte[1024];
        int len;
        OutputStream fos = resp.getOutputStream();
        resp.setContentType("image/webp");
        while ((len = fis.read(buf)) != -1){
            fos.write(buf, 0, len);
        }

        fis.close();
        fos.close();
    }

    @Override
    public void destroy() {
        System.out.println("MyServlet.destroy");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("MyServlet.init");
    }
}
