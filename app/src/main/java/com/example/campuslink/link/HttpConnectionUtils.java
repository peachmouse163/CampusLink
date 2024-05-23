package com.example.campuslink.link;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpConnectionUtils {

    //static final String IP="192.168.3.61";
    static final String IP = "192.168.84.185";
    //static final String IP="192.168.135.185";
    static final String PORT="8080";

    public static final String login="LoginServlet";
    public static final String news="NewsServlet";
    public static final String thin = "ThinServlet";
    public static final String volu = "VoluServlet";
    public static final String com = "ComServlet";
    public static final String mess = "MessageServlet";
    public static final String ques = "QuestionServlet";
    public static final String tran = "TranServlet";

    public static HttpURLConnection reGetConnection(String data) throws Exception {
        //http://192.168.3.61:8080/zzzlogin/LoginServlet/username=120012001259&password=123456&sign=1
        //http://192.168.3.61:8080/zzzlogin/LoginServlet?username=1200        &password=123456&sign=1

        //通过URL对象获取联网对象
        URL url= new URL("http://"+IP+":"+PORT+"/campuslink_war_exploded/CampusLinkRe");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }

    public static HttpURLConnection getConnection(String data,String mod) throws Exception {
        //http://192.168.3.61:8080/zzzlogin/LoginServlet/username=120012001259&password=123456&sign=1
        //http://192.168.3.61:8080/zzzlogin/LoginServlet?username=1200        &password=123456&sign=1

        //通过URL对象获取联网对象
        URL url= new URL("http://"+IP+":"+PORT+"/campuslink_war_exploded/"+mod);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }
}