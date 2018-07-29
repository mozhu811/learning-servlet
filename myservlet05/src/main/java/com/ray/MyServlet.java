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
 * @date 2018-07-09 下午7:43
 **/
public class MyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getRealPath("/spring-mvc官方手册.pdf");
        File file = new File(path);
        InputStream fis = new FileInputStream(path);
        byte[] buf = new byte[1024];
        int len;
        OutputStream fos = response.getOutputStream();
        // 响应二进制内容
        response.setContentType("bin");
        // 包装一层iso-8859-1的外衣,当客户端下载的时候解码,就是原来的utf-8的文件名,不会中文乱码
        String fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
        response.addHeader("Content-disposition", "attachment;filename=\""+fileName+"\"");
        while ((len = fis.read(buf)) != -1){
            fos.write(buf, 0, len);
        }
        fis.close();
        fos.close();
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
        super.init(config);
        System.out.println("MyServlet.init");
    }
}
