### 获取浏览器附加信息

客户端发送Http请求到服务器  
服务器端可以通过HttpServletRequest里的  
public Enumeration getHeaderNames();
可以获取到客户端的信息的属性名  
然后可以使用方法  
    public String getHeader(String name);   
获取对应的属性值
