package com.ray.servlet.myservlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-05 下午11:24
 **/
public class MyServlet extends HttpServlet {

    public MyServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        pw.println(
                "<html><head><title>HelloWorld</title><head><body>" +
                "<font color='blue'>HelloWorld</font>"+
                "</body><html>");
        pw.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
