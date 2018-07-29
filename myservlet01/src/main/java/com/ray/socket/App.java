package com.ray.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 佛祖保佑，此代码无bug，
 * 就算有，也一眼看出。
 *
 * @author ray
 * @version 1.0
 * @date 2018-07-05 下午10:32
 **/
public class App {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8080);
            Socket s = ss.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

//          直接在浏览器输入 localhost:8080
//          GET / HTTP/1.1
//          Host: localhost:8080
//          Connection: keep-alive
//          Upgrade-Insecure-Requests: 1
//          User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36
//          Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
//          Accept-Encoding: gzip, deflate, br
//          Accept-Language: zh-CN,zh;q=0.9
//          Cookie: Idea-7798877d=aa86ef31-e4df-4cc6-bf8d-66407314771c
            String str;
            while ((str = br.readLine()) != null){
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
