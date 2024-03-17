package com.example.campuslink.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuslink.MainActivity;
import com.example.campuslink.link.HttpConnectionUtils;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.StreamChangeStrUtils;
import com.example.campuslink.model.User;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class LoginViewModel {

    //handler的状态常量
    private final int LOGINSUCCESS=0;
    private final int LOGINNOTFOUND=1;
    private final int LOGINEXCEPT=2;
    private final int REGISTERSUCCESS=3;
    private final int REGISTERNOTFOUND=4;
    private final int REGISTEREXCEPT=5;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case LOGINSUCCESS:
                    //Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    //LinkToData,转换为User类型。本机判断。
                    if (!((String)msg.obj).equals("")) {
                        user = LinkToData.getUser((String) msg.obj);
                        loginActivity.startActivity(new Intent(loginContext, MainActivity.class));
                    }
                    else
                        Toast.makeText(loginContext,"账号密码错误",Toast.LENGTH_LONG).show();
                    break;
                case LOGINNOTFOUND:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case LOGINEXCEPT:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERSUCCESS:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTERNOTFOUND:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
                case REGISTEREXCEPT:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    private HashMap<String,String> userData;
    public static User user;
    private LoginActivity loginActivity;
    private Context loginContext;
    private MyThread myThread;

    public LoginViewModel(LoginActivity loginActivity) {
        user = new User();
        this.loginActivity = loginActivity;
        loginContext = loginActivity.getApplicationContext();
    }

    public LoginViewModel() {
    }

    void isExistance(String userNo, String userPassword){
        user.setInfoNo(Integer.parseInt(userNo));
        user.setInfoPassword(userPassword);

        userData = new HashMap<>();
        userData.put("userNo",user.getInfoNo()+"");
        userData.put("userPassword",user.getInfoPassword());

        myThread = new MyThread();
        myThread.setMod("login");
        myThread.setDatas(userData);
        myThread.setHandler(handler);
        myThread.start();

        //联网操作要开子线程，在主线程不能更新UI
        /*new Thread(){
            private HttpURLConnection connection;

            @Override
            public void run() {
                try {
                    //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                    // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                    //username=120012001259&password=123456&sign=1
                    String data2= "username="+ URLEncoder.encode(userNo,"utf-8")
                            +"&password="+ URLEncoder.encode(userPassword,"utf-8");
                    connection= HttpConnectionUtils.getConnection(data2,HttpConnectionUtils.login);
                    int code = connection.getResponseCode();
                    if(code==200){
                        InputStream inputStream = connection.getInputStream();
                        String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                        Message message = Message.obtain();//更新UI就要向消息机制发送消息
                        message.what=LOGINSUCCESS;//用来标志是哪个消息
                        message.obj=str;//消息主体
                        handler.sendMessage(message);
                    }
                    else {
                        Message message = Message.obtain();
                        message.what=LOGINNOTFOUND;
                        message.obj="注册异常...请稍后再试";
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
        }.start();//不要忘记开线程
*/
    }

    //清除user中的数据
    void clearUser(){
        user.setInfoNo(0);
        user.setInfoPassword("");
        loginActivity.getEtPassword().setText("");
        Toast.makeText(loginActivity,"密码错误",Toast.LENGTH_SHORT).show();
    }

}
