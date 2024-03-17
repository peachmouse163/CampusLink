package com.example.campuslink.link;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class MyThread extends Thread{

    private static final String TAG = "MyThread";

    private HttpURLConnection connection;
    private Handler handler;
    private String mod;
    private HashMap<String,String> datas;
    //handler和 message的参数
    public static final int LOGINSUCCESS=0;
    public static final int GETALLNEWS = 1;
    public static final int LOGINEXCEPT=2;
    public static final int GETALLTHIN=3;
    //mod 参数
    public final String LOGIN="login";
    public final String NEWS="mews";
    public final String THIN="thin";


    public MyThread() {

    }

    @Override
    public void run() {
        //super.run();
        switch (this.mod){
            case LOGIN:
                loginMod();
                break;
            case NEWS:
                newsMod();
                break;
            case THIN:
                thinMod();
                break;
            default:break;
        }
    }

    private void thinMod() {
        try {
            //获取所有的新闻，不需要参数
            connection = HttpConnectionUtils.getConnection("",HttpConnectionUtils.thin);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=GETALLTHIN;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=LOGINEXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }

    }

    private void newsMod() {
        try {
            //获取所有的新闻，不需要参数
            connection = HttpConnectionUtils.getConnection("",HttpConnectionUtils.news);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=GETALLNEWS;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=LOGINEXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }

    }

    private void loginMod() {
        try {
            //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
            // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
            //username=120012001259&password=123456
            String loginUrl= "username="+ URLEncoder.encode(datas.get("userNo"),"utf-8")
                    +"&password="+ URLEncoder.encode(datas.get("userPassword"),"utf-8");
            connection= HttpConnectionUtils.getConnection(loginUrl,HttpConnectionUtils.login);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=LOGINSUCCESS;//用来标志是哪个消息
                message.obj=str;//消息主体
                Log.d(TAG, "loginMod: "+str);
                handler.sendMessage(message);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=LOGINEXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public HashMap<String, String> getDatas() {
        return datas;
    }

    public void setDatas(HashMap<String, String> datas) {
        this.datas = datas;
    }
}
