package com.ray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * 描述:
 * 获取浏览器的附加信息
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-09 下午4:24
 **/
public class MyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> enu = request.getHeaderNames();
//        Header信息
//        host : localhost:8080
//        connection : keep-alive
//        content-length : 23
//        cache-control : max-age=0
//        origin : http://localhost:8080
//        upgrade-insecure-requests : 1
//        content-type : application/x-www-form-urlencoded
//        user-agent : Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36
//        accept : text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//        referer : http://localhost:8080/servlet03/
//        accept-encoding : gzip, deflate, br
//        accept-language : zh-CN,zh;q=0.9
//        cookie : JSESSIONID=71633EB63C57E7FE690C660D2F0BC19F; Idea-7798877d=aa86ef31-e4df-4cc6-bf8d


        /*输出到页面*/
        // 设置文本类型和编码
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<html>");
        out.println("<head><title>My Servlet</title></head>");
        out.println("<body>");
        out.println("<table border='1' align='center' width='80%'>");
        while (enu.hasMoreElements()){
            String property = enu.nextElement();
            out.println("<tr><td>"+property+"</td><td>"+request.getHeader(property)+"</td></tr>");
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
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
