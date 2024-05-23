package com.example.campuslink.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.campuslink.MainActivity;
import com.example.campuslink.TeacherActivity;
import com.example.campuslink.link.HttpConnectionUtils;
import com.example.campuslink.link.LinkToData;
import com.example.campuslink.link.MyThread;
import com.example.campuslink.link.ReThread;
import com.example.campuslink.link.StreamChangeStrUtils;
import com.example.campuslink.model.Login;
import com.example.campuslink.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class LoginViewModel {

    private final String TAG = "LoginViewModel";

    //handler的状态常量
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){//消息机制，用来在子线程中更新UI
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){//具体消息，具体显示
                case ReThread.SUCCESS:
                    //Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    //LinkToData,转换为User类型。本机判断。
                    Log.d(TAG, "handleMessage: "+(String) msg.obj);
                    if (!((String)msg.obj).equals("")) {
                        //user = LinkToData.getUser((String) msg.obj);
                        Gson gson = new Gson();
                        user = gson.fromJson((String) msg.obj,User.class);
                        switch (user.getInfoIdentity()){
                            case 0:
                                //学生
                                loginActivity.startActivity(new Intent(loginContext, MainActivity.class));
                                break;
                            case 1:
                                //教师
                                loginActivity.startActivity(new Intent(loginContext, TeacherActivity.class));
                                break;
                            case 2:
                                //部门,或许不存在合并为教师。
                                break;
                            case 3:
                                //后台
                                break;
                            default:
                                break;
                        }
                    }
                    else
                        Toast.makeText(loginContext,"账号密码错误",Toast.LENGTH_LONG).show();
                    break;
                case ReThread.EXCEPT:
                    Toast.makeText(loginContext,(String)msg.obj,Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


    private HashMap<String,Object> userData;
    public static User user;
    private LoginActivity loginActivity;
    private Context loginContext;
    private ReThread reThread;

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

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String data = gson.toJson(user);

        Log.d(TAG, "isExistance: user——json:data:"+data);
        userData.put("data",data);
        reThread = new ReThread(userData,handler,"login");
        reThread.start();
    }

    //清除user中的数据
    void clearUser(){
        user.setInfoNo(0);
        user.setInfoPassword("");
        loginActivity.getEtPassword().setText("");
        Toast.makeText(loginActivity,"密码错误",Toast.LENGTH_SHORT).show();
    }

}
