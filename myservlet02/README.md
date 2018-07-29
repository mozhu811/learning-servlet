### 解决乱码问题的三种方式

#### 第一种 通过jdk
例子:  
```java
        String name = request.getParameter("name");
        // 乱码
        System.out.println(name);
        name = new String(name.getBytes("iso-8859-1"),"utf-8");
        // 正常
        System.out.println(name);
```
这种方式对GET和POST都有效,但是非常的啰嗦冗余,不推荐

#### 第二种 通过HttpServletRequest
例子:
```java
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        // 正常
        System.out.println(name);
```
这种方式只能对POST请求生效,因为GET请求在客户端已经将字节数据通过某种字符集转换成了字符数据,如果再setCharacterEncoding则会更乱  

#### 第三种 通过修改Tomcat配置文件server.xml
修改Tomcat配置文件server.xml  
在Connector节点添加URIEncoding="utf-8"
```xml
    <Connector port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443"
               URIEncoding="utf-8" />
```
```java
        String value = request.getParameter("name");
        // 正常
        System.out.println(value);
```