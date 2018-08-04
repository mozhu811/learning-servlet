* [项目记录](#项目记录)
* [HTTP协议相关](#http协议相关)
 * [HTTP协议的特征](#http协议的特征)
 * [请求类型](#请求类型)
    * [GET和POST的区别](#get和post的区别)
 * [HTTP数据包](#http数据包)
    * [GET方式提交（Java代码控制台打印为例）](#get方式提交java代码控制台打印为例)
* [Servlet继承结构](#servlet继承结构)
* [Servlet的生命周期](#servlet的生命周期)
 * [Tomcat执行Servlet过程(伪代码)](#tomcat执行servlet过程伪代码)
* [Servlet的作用](#servlet的作用)
 * [Servlet如何获取表单数据](#servlet如何获取表单数据)
 * [解决Servlet中的中文乱码问题](#解决servlet中的中文乱码问题)
 * [关于Servlet的线程安全的问题](#关于servlet的线程安全的问题)
 * [Servlet文件上传](#servlet文件上传)
 * [自启动的Servlet](#自启动的servlet)
 * [Servlet的常见对象](#servlet的常见对象)
    * [ServletContext](#servletcontext)
       * [ServletContext的主要用法](#servletcontext的主要用法)
    * [ServletConfig](#servletconfig)
    * [Cookie](#cookie)
       * [什么是Cookie](#什么是cookie)
       * [Cookie中如何传递中文信息](#cookie中如何传递中文信息)
    * [HttpSession](#httpsession)
       * [什么是HttpSession](#什么是httpsession)
       * [HttpSession的运行过程](#httpsession的运行过程)
       * [HttpSession的生命周期](#httpsession的生命周期)
* [编写一个自定义Servlet](#编写一个自定义servlet)
 * [自定义Servlet类继承HttPServlet,并且重写doGet和doPost方法](#自定义servlet类继承httpservlet并且重写doget和dopost方法)
 * [编写到运行Servlet的步骤（不适用IDE的方法）](#编写到运行servlet的步骤不适用ide的方法)
## 项目记录
+ myServlet01: 实现简单的Servlet的Hello World
+ myServlet02: 实现三种乱码处理方案
+ myServlet03: 实现通过Servlet的API获取用户浏览器的基本信息
+ myServlet04: 实现通过IO流向客户端传输图片并展示
+ myServlet05: 实现文件下载
+ myServlet06: 实现通过JDBC对数据库进行操作,待施工

本笔记为个人学习思考解惑记录,多方面引用互联网资料。
若有纰漏,欢迎指正。

## HTTP协议相关
### HTTP协议的特征

1. 单向性：客户端和服务端建立连接依靠于客户端发送请求，如果客户端不发送请求，服务端不会主动发送主句到客户端
2. 无状态：HTTP对于事务处理没有记忆能力。无法“断点续传”
3. 灵活：HTTP允许传输任意类型的数据对象。正在传输的类型有Content-Type加以标记
4. 简单快速：客户端向服务器发送请求时，只需传送请求方法和路径。请求方法常用的有GET,POST,HEAD,PUT,DELETE。每种方法规定了客户端与服务器建立连接的类型不同。由于HTTP协议简单，使得HTTP服务器的程序规模小，因而通信速度快

------------


### 请求类型
#### GET和POST的区别

1. GET方式提交表单时，表单数据会在地址栏显示。而POST不会
2. GET方式提交表单时，表单数据长度是有限的（地址栏长度有限）。而POST理论上没有限制
3. GET方式提交表单时，表单数据都是以字符方式提交。而POST既可以用字符也可以用字节，默认用字符。
4. GET方式提交表单会在Http数据包中的第一行出现，而POST提交表单会在空一行的body中出现
5. GET请求能够被缓存，POST请求不能被缓存下来

------------


### HTTP数据包
#### GET方式提交（Java代码控制台打印为例）

```java
public class App {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str;
            while ((str = br.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```
运行以上代码后，在浏览器输入localhost:8080  
则在控制台打印以下内容
```text
          GET / HTTP/1.1
          Host: localhost:8080
          Connection: keep-alive
          Upgrade-Insecure-Requests: 1
          User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36
          Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
          Accept-Encoding: gzip, deflate, br
          Accept-Language: zh-CN,zh;q=0.9
          Cookie: Idea-7798877d=aa86ef31-e4df-4cc6-bf8d-66407314771c
```

------------


## Servlet继承结构

![image](https://www.crzmy.com/wp-content/uploads/2018/07/Servlet.png) 

------------


## Servlet的生命周期

Servlet接口中定义了作为一个Servlet在整个生命周期中应该拥有三个阶段
1. 初始化
2. 服务 service
3. 销毁 destroy 

```text
Servlet的生命周期是由容器管理的,Servlet初始化以后立即调用init()方法，开发者可以重写该方法让Servlet初始化以后执行相应的操作
```

------------


### Tomcat执行Servlet过程(伪代码)

当客户端请求Servlet的时,Tomcat获取HTTP数据包信息,得到了头部信息中的
```text
GET /myservlet/hello.do HTTP/1.1
```
假如有一个类存储HTTP相关信息
```java
public class HttpInfo{
    private String method;
    private String uri;
    private String protocol;
    ....
    /* getter and setter */
}
```
```java
// Tomcat新建一个ServerSocket对象,监听端口
ServerSocket socket = new ServerSocket(监听端口号);
Socket s;
boolean flag = true;
while(flag){
    // 获取对应客户端的Socket对象
    s = socket.accept();
}
// 通过IO流去读取数据
BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(),"iso-8859-1");
String line;
HttpInfo info = new HttpInfo();
HttpServletRequest request;
HttpServletResponse response;
while((line = br.readLine()) != null){
    // Tomcat解析HTTP数据包
    // 例:拆分头部第一行的信息,获取请求方法,URI,协议版本
    String[] arr = line.split("\\s");
    info.setMethod(arr[0]);
    // 存储的已经处理字符串后得到的uri
    info.setUri(arr[1]);
    info.setProtocol(arr[2]);
    // 处理第一行以外的其他信息
    ...
    request = new HttpServetRequest();
    request.setMethod(arr[0]);
    // 例如获取表单数据
    ...

    // Tomcat解析客户端的相关信息,比如发送请求的客户端的IP地址
    response.setSocket(s);
}

// 对请求的HTTP数据包解析完毕后,处理请求
// Tomcat在启动时就会解析项目下的部署描述文件web.xml
// 在里面我们在<servlet-class>标签里配置了我们Servlet类的全路径,通过反射获取到Class对象,进而通过Class.newInstance()方法生成实例化对象.
Class clazz = Class.forName(配置文件中Servlet类的全路径);
// 多态性,父类引用指向子类对象
Servlet obj = clazz.newInstance(); 
// Servlet对象初始化之后的相关操作
// 比如,人到了公司打卡后,职工对象就初始化好了,但是你还不能直接去工作(service)
// 还要去找到你的办公位置,还要把电脑开机等操作,这就是init()里应该做的事情
// 调用的是重写后的init()方法
obj.init(); 
// 说明是多线程的,只是例子,这种方法开启线程很蠢
new Thread(new St(request, response)).start();
...
...
// 到了容器要销毁Servlet对象之前
// 容器就会去调用destroy()方法
// 还是那个上班的例子
// 办公楼晚上要锁门了,要把所有人都赶走,但是职工必须得在赶走之前,把文档,代码这些保存好,然后把电脑关机,关掉办公室的灯等动作
// 这就是destroy()方法做的类似事情
```
模拟Tomcat里多线程
```java
public St implements Runnable{
    private Servlet obj;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public St(Servlet obj, HttpServletRequest request, HttpServletResponse response){
        this.obj = obj;
        this.request = request;
        this.response = response;
    }
    @Override
    public void run(){
        // 执行HttpServlet里的service()方法
        this.obj.service(request, response);
        // 执行后续操作
        // 判断request里保存的请求方式
        // GET POST ...
        // 根据不同的方式请求不同的方法,以GET为例
        // 会去调用重写后的doGet方法
        ...
    }
}
```
> 总结:

Servlet的生命周期是由容器管理的,分为init,service和destroy三个阶段.  
当有客户端第一次访问这个Servlet时,容器会立即初始化这个Servlet对象,初始化结束以后立即调用init()方法,并且在新的线程中调用service()方法.  
容器会将初始化后的Servlet对象进行缓存,当有客户端再次请求该Servlet时,容器不会再进行创建,而是直接在新的线程里调用service()方法.  
当容器关闭时,容器会在销毁这个Servlet对象之前,调用一次destroy()方法.

------------


## Servlet的作用

1. 获取表单数据
2. 获取浏览器的附加信息
3. 处理数据(本身不具备处理数据的能力,比如持久化.它是通过调用其他的处理数据的方式来实现的,比如JDBC...)
4. 给客户端产生一个响应
5. 在响应中添加附加信息

------------


### Servlet如何获取表单数据

在HttpServletRequest里有几个方法.
```java
1. String getParameter(String name)
	这个方法处理键值对比如key=value,通过key来获取对应的value.但是这个方法不能获取页面上checkbox的value,因为他的数据格式是key=value1&key=value2&key=value3
2. String[] getParameterValues(String name)
	该方法就可以处理checkbox的value,它可以获取对应key的所有value
3. String getQueryingString(String str)
	这个方法可以获取URL中的查询字符串(?后面的字符串)
4. Map getParameterMap()
	这个方法可以获取请求参数将其封装成Map数据格式
```

------------


### 解决Servlet中的中文乱码问题

首先要知道的是Tomcat的默认字符集是ISO-8859-1
1. 通用,通过jdk的new String产生新的对应编码的String对象

```java
/*解决中文乱码问题 第一种方式
GET和POST都有效,但不推荐,会有大量冗余代码*/

String name = request.getParameter("name");
// 乱码
System.out.println(name);
name = new String(name.getBytes("iso-8859-1"),"utf-8");
// 正常
System.out.println(name);
```
2. 只适用POST,通过HttpServletRequest的API

```java
/*解决中文乱码问题 第二种方式
只适用于POST请求,使用request里的内置方法*/

request.setCharacterEncoding("utf-8");
String name = request.getParameter("name");
// 正常
System.out.println(name);
```
3. 通用,修改Tomcat配置文件server.xml

```xml
<Connector port="8080" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8443"
           URIEncoding="utf-8" />
```
```java
/*解决中文乱码问题 第三种方式
* 修改Tomcat配置文件server.xml
 * 在Connector节点添加URIEncoding="utf-8"
 * */
String value = request.getParameter("name");
// 正常
System.out.println(value);
```

------------


### 关于Servlet的线程安全的问题

Servlet是一个线程不安全的技术,在Servlet中定义成员变量时,如果一定要定义成员变量,那么以读取为主.尽量不要同时读同时写.如果一定有这样的需求,则需要加锁.
~~interface SingeThreadModel~~ 是Servlet提供的一个标识接口,该接口标识实现了该接口的Servlet的运行方式会由并行化改为串行化.效率低下,所以该接口在解决线程安全的问题时,已经不推荐使用了.

------------

### Servlet文件上传

1. 在实现文件上传是,表单的提交方式必须是POST方式提交,因为POST请求才支持让表单数据以字节的方式提交,而GET只能是字符提交.
2. 在form表单中修改请求的信息,将原来默认的字符提交修改为字节提交:通过修改form标签中的enctype属性,将其值改为multipart/form-data,该属性表示当前表单为字节格式,当服务器接收到当前数据包的时候,则不会去解析,所以也无法使用request.getParameter()去获取表单数据.
3. 在2的情况下,如果需要处理表单中的内容,需要通过request对象的getInputStream()方法来获取一个通信流,通过对流对象的操作完成相应的表单处理.但是相比更推荐使用Apache的common-fileupload组件.

------------


### 自启动的Servlet
自启动的Servlet的实例化不依赖于客户端请求,而是依赖于容器.当容器启动时就回去初始化这个Servlet.
在web.xml文件中在对应Servlet的<servlet>节点最后一行添加
```xml
<load-on-startup>1</load-on-startup>
```
其中数字表示启动优先级,数字越小,优先级越高

------------

### Servlet的常见对象
#### ServletContext
##### ServletContext的主要用法
1. 相对路径转绝对路径
```java
ServletContext sc = this.getServletContext();
String rootPath = sc.getRealPath("/file.txt");
File file = new File(rootPath);
```
2. 获取容器附加信息
```java
// 获取Servlet的主版本号
int getMajorVersion();
// 获取Servlet的副版本号
int getMinorVersion();
// 获取服务器的版本信息
String getServerInfo();
...
...
```
3. 全局容器
Servlet通过以下两个API完成对自身的添加和读取数据的操作.
```java
// 添加数据
void setAttribute(String name, Object value);
// 获取数据
Object getAttribute(String name);
```
注意:
建议不要存储业务数据,数据会随着生命周期一直在内存中,增大了服务器的负担.其次也避免了与数据库数据同步的问题.

4. 读取web.xml里的配置信息
```xml
<context-param>
    <param-name>key</param-name>
    <param-value>value</param-value>
</context-param>
```
通过以下方式可以获取该节点信息
```java
ServletContext sc = this.getServletContext();
String value = sc.getInitParameter("key");
```
该配置信息是全局可访问,可以配置多个<context-param>,但是在一个<context-param>里只能有一个key/value配置.
#### ServletConfig
作用:用于读取在web.xml的<servlet>节点中的配置信息.在<servlet>节点中可以加入<init-param>节点.
	
```xml
<servlet>
    <servlet-name>MyServlet</servlet-name>
    <servlet-class>com.ray.MyServlet</servlet-class>
    <init-param>
        <param-name>key</param-name>
        <param-value>value</param-value>
    </init-param>
</servlet>
```
通过以下方式可以获取配置的key/value
```java
// 手动写key获得value
ServletConfig config = this.getServletConfig();
String value = sc.getInitParameter("key")

// 可以使用另一个方法来获得所有的key/value
ServletConfig config = this.getServletConfig();
Enumeration parameterNames = sc.getInitParameterNames();
String value = "";
while (parameterNames.hasMoreElements()){
    value = sc.getInitParameter(parameterNames.nextElement());
    System.out.println(value);
}
```
同样在一个<servlet>节点中可以配置多个<init-param>,但是一个<init-param>只能配置一个key/value信息.  
但是这个配置信息是只能在对应配置的Servlet才能访问到.其他Servlet无法访问.
#### Cookie
##### 什么是Cookie
Cookie是一个依赖于客户端维持会话状态的对象  
Cookie的特点:如果程序需要给客户端浏览器返回一个Cookie,那么则需要自己来创建;Cookie的结构为key/value结构.
Cookie分为两种:
1. 状态Cookie: 随着浏览器的生命周期存在,浏览器关闭,则对象消失
2. 持久化Cookie: Cookie对象持久化到磁盘中,当Cookie的存活时间到达时,浏览器不会再在请求中传递该Cookie.对于这些Cookie文件,浏览器会自己管理.通过setMaxAge(int s)方法来将Cookie持久化.

当需要使用Cookie对象向客户端浏览器传递数据,数据本身不能是中文.  
当客户端浏览器请求Servlet是,客户端浏览器会将该服务器以前写回的所有Cookie对象在请求中传递.  
  
可以通过以下简单的实例来实现判断用户访问
```java
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setContentType("text/html;charset=utf-8");
    PrintWriter pw = response.getPrintWriter();
    Cookie[] cookies = request.getCookies();
    if(cookies == null || cookies.length <= 0){
        pw.println("欢迎光临!");
        Cookie c = new Cookie("cookie","cookie");
        // 持久化Cookie,如果没有这一句则是状态Cookie
        c.setMaxAge(120);
        response.addCookie(c);
    } else {
        pw.println("欢迎回来!");
    }
}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
}
```

##### Cookie中如何传递中文信息
1. 通过可逆的加密算法
```java
/* 使用一个Java的加密字符串工具包 EncryptUtils,自行百度或编写 */
....
Cookie[] cookies = request.getCookies();
if(cookies == null || cookies.length<=0){
    String str = "今天是二零一八年七月十二日";
    // 加密字符串
    String ciphertext = EncryptUtils.encrypt(str);
    Cookie c = new Cookie("cookie", ciphertext);
    c.setMaxAge(60*60*24);
    response.addCookie(c);
} else {
    pw.println("欢迎回来!");
    Cookie c = null;
    for(Cookie cookie : cookies){
        if("cookie".equals(cookie.getName())){
            c = cookie;
            break;
        }
    }
    if(c != null){
        // 解密字符串
        String text = EncryptUtils.decrypt(c.getValue());
        pw.println("欢迎回来!" + text);
    }
}
```
2. 通过字符编码
```java
/* 
使用了java.net包下的操作URL编码的包
修改了上面代码的加密解密两行,其他完全一致 */
....
Cookie[] cookies = request.getCookies();
if(cookies == null || cookies.length<=0){
    String str = "今天是二零一八年七月十二日";
    // 加密字符串
    String ciphertext = URLEndoder.encode(str, "utf-8");
    Cookie c = new Cookie("cookie", ciphertext);
    // 单位是秒,60 * 60 * 24 = 1天
    c.setMaxAge(60*60*24);
    response.addCookie(c);
} else {
    pw.println("欢迎回来!");
    Cookie c = null;
    for(Cookie cookie : cookies){
        if("cookie".equals(cookie.getName())){
            c = cookie;
            break;
        }
    }
    if(c != null){
        // 解密字符串
        String text = URLDecoder.decode(c.getValue(), "utf-8");
        pw.println("欢迎回来!" + text);
    }
}
```

------------


#### HttpSession
##### 什么是HttpSession
HttpSession对象可以建立客户端与服务器之间的对话,但会话是否建立.取决于服务器是否为这个客户端创建了HttpSession对象.如果是,则HttpSession就表示当前客户端与服务器的会话已经建立.进而该HttpSession对象只为该客户端使用,不会与其他客户端共享.

##### HttpSession的运行过程
首先HttpServletRequest中有两个获取HttpSession的方法,源码如下,删除了部的注释

```java
/**
 *
 * Returns the current HttpSession associated with
 * this request or, if there is no
 * current session and create is true, returns 
 * a new session.
 *
 * If create is false
 * and the request has no valid HttpSession,
 * this method returns null.
 *
 * @param create true to create a new session for
 * this request if necessary;
 * false to return null if there's no current session
 *
 * @return 	the HttpSession associated with this request or null
 * if create is false and the request has no valid session
 *
 */

public HttpSession getSession(boolean create);
    
/**
 *
 * Returns the current session associated with this request,
 * or if the request does not have a session, creates one.
 * 
 * @return	the HttpSession associated with this request
 */
public HttpSession getSession();
```
阅读了上面的代码可以知道,getSession()和getSession(true)是同一个执行结果.  
当客户端浏览器访问Servlet时,如果代码中调用了getSession(boolean create)或者getSession(),那么服务器会根据传递的参数来执行不同的逻辑.如果create参数是true或者没有参数则会在底层的SessionId/HttpSession对象映射中寻找是否有该客户端的HttpSession对象,如果没有则创建一个新的HttpSession对象并且把该对象的SessionId通过状态Cookie返回给客户端.如果create参数是false,同样也会在底层的SessionId/HttpSession对象映射中寻找是否有该客户端的HttpSession对象,但是如果没有对应的HttpSession对象,则会返回null.

------------


##### HttpSession的生命周期

1. 创建: 当有客户端浏览器第一次请求Servlet时,该Servlet中调用了HttpServletRequest里的getSession(boolean create)或者request.getSession()方法时,则会创建一个HttpSession对象.
2. 销毁:
    1. 使用HttpSession里的invalidate()方法,该方法会直接是该Session直接销毁,并且取消绑定的任何对象.
    ```java
    /**
     *
     * Invalidates this session then unbinds any objects bound
     * to it. 
     *
     * @exception IllegalStateException	if this method is called on an
     *					already invalidated session
     *
     */

    public void invalidate();
    ```
    2. 修改Tomcat里的conf目录下的web.xml文件,默认是30分钟,超过配置时间则会销毁,对Tomcat里所有项目有效
    ```xml
    <session-config>
        <session-timeout>30</session-timeout>
    </session-config>
    ```
    3. 修改本项目的web.xml,同理也是超过配置时间则会自动销毁,但是仅对本项目有效.
    ```xml
    <session-config>
        <session-timeout>1</session-timeout>
    </session-config>
    ```

------------


## 编写一个自定义Servlet

### 自定义Servlet类继承HttPServlet,并且重写doGet和doPost方法

```java
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

```
### 编写到运行Servlet的步骤（不适用IDE的方法）

1. 编写自定义Servlet类继承HttpServlet
2. 重写doGet和doPost方法
3. 使用javac工具编译代码
4. 在Tomcat的webapp目录下创建项目名称文件夹，在该文件夹下创建WEB-INF目录，继续在WEB-INF下创建classes目录，在里面创建包结构
5. 把javac生成的.class文件放入对应的包结构目录下
6. 在WEB-INF目录下创建web.xml
7. 编辑web.xml,配置Servlet
```xml
<web-app>
		<display-name>ArchetypeCreatedWebApplication</display-name>

		<servlet>
			<servlet-name>myServlet</servlet-name>
			<servlet-class>com.ray.servlet.myservlet.MyServlet</servlet-class>
		</servlet>

		<servlet-mapping>
			<servlet-name>myServlet</servlet-name>
			<url-pattern>*.do</url-pattern>
		</servlet-mapping>
</web-app>
```
8. 启动Tomcat容器
9. 在浏览器地址栏输入
```text
localhost:8080/(servlet名字)/(请求路径)
本例为：localhost:8080/myServlet/hello.do
```
10. 结果展示  
![image](https://note.youdao.com/yws/api/personal/file/WEB087b923b25abb635430f047f7bebbd6b?method=download&shareKey=64398d4c6780b770074e3f9d3fab43f7) 
