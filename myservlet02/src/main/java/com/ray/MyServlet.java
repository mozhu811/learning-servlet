package com.ray;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-09 下午3:28
 **/
public class MyServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*解决中文乱码问题 第二种方式
         只适用于POST请求,使用request里的内置方法*/

        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        // 正常
        System.out.println(name);


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*解决中文乱码问题 第一种方式
          GET和POST都有效,但不推荐,会有大量冗余代码*/

        String name = request.getParameter("name");
        // 乱码
        System.out.println(name);
        name = new String(name.getBytes("iso-8859-1"),"utf-8");
        // 正常
        System.out.println(name);

        /*解决中文乱码问题 第三种方式
         * 修改Tomcat配置文件server.xml
         * 在Connector节点添加URIEncoding="utf-8"
         * */
        String value = request.getParameter("name");
        // 正常
        System.out.println(value);
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
