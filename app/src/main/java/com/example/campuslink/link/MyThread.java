package com.example.campuslink.link;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.campuslink.communicate.ComActivity;
import com.example.campuslink.model.CommunicationModel;

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
    public static final int EXCEPT=2;
    public static final int GETALLTHIN=3;
    public static final int GETALLVOLU=4;
    public static final int GETMYCOM=5;
    public static final int GETFRIEND=6;
    public static final int INITDATA=7;
    public static final int VOLUSECC=8;
    public static final int ALLMESS=9;
    public static final int ALLCOLL=10;
    public static final int ALLSIGN=11;
    public static final int QUESTIONS = 12;
    public static final int ALLTRAN = 13;

    public static final int fiveSecond = 5000;

    //mod 参数
    public final String LOGIN="login";
    public final String NEWS="mews";
    public final String THIN="thin";
    public final String VOLU="volu";
    public final String COM="com";
    public final String SENDCOM="sendcom";
    public final String VOLUSIGN="volusign";
    public final String GETMESS="getMess";
    public final String GETQUES="getQues";
    public final String GETTRAN="getTran";

    public final String SECOND5 ="secondFive";
    //标志
    public static boolean comModFlag = true;
    public static boolean sendComFlag = true;


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
            case VOLU:
                voluMod();
                break;
            case COM:
                comMod();
                break;
            case SENDCOM:
                sendComMod();
                break;
            case VOLUSIGN:
                voluSign();
                break;
            case GETMESS:
                getAllMess();
                break;
            case GETQUES:
                getAllQues();
                break;
            case GETTRAN:
                getAllTran();
                break;
            case SECOND5:
                fiveSecond();
                break;
            default:break;
        }
    }

    private void fiveSecond() {
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        Message message = Message.obtain();//更新UI就要向消息机制发送消息
        message.what=fiveSecond;//用来标志是哪个消息
        message.obj="";//消息主体
        handler.sendMessage(message);
    }

    private void getAllTran() {
        try {
            //http://192.168.3.61:8080/campuslink/VoluServlet?mod=updata&voluId=1&voluState=0;12001260
            //获取所有的ques，不需要参数
            connection = HttpConnectionUtils.getConnection("",HttpConnectionUtils.tran);
            int code = connection.getResponseCode();
            //Log.d(TAG, "getAllQues: "+code);
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=ALLTRAN;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
                Log.d(TAG, "getAlltran: "+str);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常0
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    private void getAllQues() {
        try {
            //http://192.168.3.61:8080/zzzlogin/VoluServlet?mod=updata&voluId=1&voluState=0;12001260
            //获取所有的ques，不需要参数
            connection = HttpConnectionUtils.getConnection("",HttpConnectionUtils.ques);
            int code = connection.getResponseCode();
            //Log.d(TAG, "getAllQues: "+code);
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=QUESTIONS;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
                Log.d(TAG, "getAllQues: "+str);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常0
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    private void getAllMess() {
        try {
            //http://192.168.3.61:8080/zzzlogin/VoluServlet?mod=updata&voluId=1&voluState=0;12001260
            //获取所有的新闻，不需要参数
            String voluUrl="infoNo="+ URLEncoder.encode(datas.get("infoNo"),"utf-8");
            connection = HttpConnectionUtils.getConnection(voluUrl,HttpConnectionUtils.mess);
            int code = connection.getResponseCode();
            Log.d(TAG, "getAllMess: "+code);
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=ALLMESS;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
                Log.d(TAG, "getAllMess: "+str);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常0
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    private void voluSign() {
        try {
            //http://192.168.3.61:8080/zzzlogin/VoluServlet?mod=updata&voluId=1&voluState=0;12001260
            //获取所有的新闻，不需要参数
            String voluUrl= "mod="+ URLEncoder.encode(datas.get("mod"),"utf-8")
                    +"&voluId="+ URLEncoder.encode(datas.get("voluId"),"utf-8")
                    +"&infoNo="+ URLEncoder.encode(datas.get("voluState"),"utf-8");
            connection = HttpConnectionUtils.getConnection(voluUrl,HttpConnectionUtils.volu);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=VOLUSECC;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
                //Log.d(TAG, "voluMod: "+(String) message.obj);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    private void voluMod() {
        try {
            //获取所有的新闻，不需要参数
            String voluUrl = "mod="+ URLEncoder.encode("show","utf-8");
            connection = HttpConnectionUtils.getConnection(voluUrl,HttpConnectionUtils.volu);
            int code = connection.getResponseCode();
            if(code==200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=GETALLVOLU;//用来标志是哪个消息
                message.obj=str;//消息主体
                handler.sendMessage(message);
                //Log.d(TAG, "voluMod: "+(String) message.obj);
            }
        } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
            e.printStackTrace();
            Message message = Message.obtain();
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
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
            message.what=EXCEPT;
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
            message.what=EXCEPT;
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
            message.what=EXCEPT;
            message.obj="服务器异常...请稍后再试";
            handler.sendMessage(message);
        }
    }

    private void comMod() {
        while (comModFlag){
            try {
                //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                //infoNo=12001259

                Log.d(TAG, "comMod: "+datas.get("mod"));

                String loginUrl= "infoNo="+ URLEncoder.encode(datas.get("infoNo"),"utf-8")
                        +"&mod="+ URLEncoder.encode(datas.get("mod"),"utf-8");
                connection= HttpConnectionUtils.getConnection(loginUrl,HttpConnectionUtils.com);
                int code = connection.getResponseCode();
                if(code==200){
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                    Message message = Message.obtain();//更新UI就要向消息机制发送消息
                    message.what=GETMYCOM;//用来标志是哪个消息
                    message.obj=str;//消息主体
                    //1;2022-02-22 12:30:00;12001259;12001260;测试内容测试聊天第1条
                    Log.d(TAG, "comMod: "+str);
                    handler.sendMessage(message);
                }

                Thread.sleep(1500);
                comModFlag = false;
            } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                e.printStackTrace();
                Message message = Message.obtain();
                message.what=EXCEPT;
                message.obj="服务器异常...请稍后再试";
                handler.sendMessage(message);
            }


        }

    }

    private void sendComMod() {
        while (sendComFlag){
            //传入对象，发送至服务器，保存数据，数据库插入
            try {
                //等待data有新的内容
                do {
                    Message message = Message.obtain();
                    message.what = INITDATA;
                    message.obj = 0;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                }while (ComActivity.data.isEmpty());
                //封装成传输数据的键值对,无论get还是post,传输中文时都要进行url编码（RULEncoder）
                // 如果是在浏览器端的话，它会自动进行帮我们转码，不用我们进行手动设置
                //infoNo=12001259
                //infoNo=12001259&mod=insert&datetime=2023-02-22 14:30:00&infoNoB=12001260&Content=send发送信息测试
                String loginUrl= "infoNo="+ URLEncoder.encode(datas.get("infoNo"),"utf-8")
                        +"&mod="+ URLEncoder.encode(datas.get("mod"),"utf-8")
                        +"&datetime="+ URLEncoder.encode(datas.get("datetime"),"utf-8")
                        +"&infoNoB="+ URLEncoder.encode(datas.get("infoNoB"),"utf-8")
                        +"&Content="+ URLEncoder.encode(datas.get("Content"),"utf-8");
                connection= HttpConnectionUtils.getConnection(loginUrl,HttpConnectionUtils.com);
                int code = connection.getResponseCode();
                if(code==200){
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                    Message message = Message.obtain();//更新UI就要向消息机制发送消息
                    message.what=GETFRIEND;//用来标志是哪个消息
                    message.obj=str;//消息主体
                    //1;2022-02-22 12:30:00;12001259;12001260;测试内容测试聊天第1条
                    Log.d(TAG, "comMod: "+str);
                    handler.sendMessage(message);
                    //发送完就清理掉。
                    ComActivity.data.clear();
                }
            } catch (Exception e) {//会抛出很多个异常，这里抓一个大的异常
                e.printStackTrace();
                Message message = Message.obtain();
                message.what=EXCEPT;
                message.obj="服务器异常...请稍后再试";
                handler.sendMessage(message);
                return;
            }
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
