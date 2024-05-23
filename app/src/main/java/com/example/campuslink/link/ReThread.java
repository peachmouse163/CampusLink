package com.example.campuslink.link;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.campuslink.model.CommunicationModel;
import com.example.campuslink.model.User;
import com.example.campuslink.model.VoluModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class ReThread extends Thread{
    private static final String TAG = "ReThread";

    private HttpURLConnection connection;
    private Handler handler;
    private String mod;
    private HashMap<String,Object> datas;
    private int FLAG = 0;

    //标志常量
    public static final int EXCEPT=0;
    public static final int SUCCESS=1;

    public ReThread(HashMap<String,Object> datas,Handler handler,String mod) {
        this.datas = datas;//传递用data    +   其他数据
        this.handler = handler;
        this.mod = mod;
    }

    public ReThread(HashMap<String,Object> datas,Handler handler,int flag) {
        this.datas = datas;//传递用data    +   其他数据
        this.handler = handler;
        this.FLAG = flag;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: inter"+FLAG);
        if (FLAG != 0){
            switch (FLAG){
                case 1000://首次登陆修改密码
                    changePwd();
                    break;
                case 1001:
                    getAllVolu();
                    break;
                case 1002:
                    getAllPay();
                    break;
                case 1003:
                    getDepartment();
                    break;
                case 1004:
                    addQues();
                    break;
                case 1005:
                    getDQues();
                    break;
                case 1006:
                    qtaChange();
                    break;
                case 1007:
                    qtaRe();
                    break;
                case 1008:
                    getAllQues();
                    break;
                case 1009:
                    addSub();
                    break;
                case 1010:
                    sendMess();
                    break;
                case 1011:
                    getMyMess();
                    break;
                case 1012:
                    findAllUser();
                    break;
                case 1013:
                    getMyColl();
                    break;
                case 1014:
                    getMySign();
                    break;
                case 1015:
                    readed();
                    break;
                case 1016:
                    returnPerson();
                    break;
                case 1017:
                    updateColl();
                    break;
                case 1018:
                    getDepartMess();
                    break;
                case 1019:
                    getDepartColl();
                    break;
                case 1020:
                    getDepartSign();
                    break;
                case 1021:
                    addThin();
                    break;
                case 1022:
                    addVolu();
                    break;
            }
        }else {
            try {
                String infoUrl = "mod="+ URLEncoder.encode(mod,"utf-8")
                        +"&data="+ URLEncoder.encode((String) datas.get("data"),"utf-8");
                connection = HttpConnectionUtils.reGetConnection(infoUrl);
                int code = connection.getResponseCode();
                if (code == 200){
                    InputStream inputStream = connection.getInputStream();
                    String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                    Message message = Message.obtain();//更新UI就要向消息机制发送消息
                    message.what=SUCCESS;//用来标志是哪个消息
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
    }

    private void addVolu() {
        try{
            String httpUrl = "mod=addVolu&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "addThin: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void addThin() {
        try{
            String httpUrl = "mod=addThin&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "addThin: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getDepartSign() {
        try{
            String httpUrl = "mod=getDepartSign&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "returnPerson: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getDepartColl() {
        try{
            String httpUrl = "mod=getDepartColl&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "returnPerson: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getDepartMess() {
        try{
            String httpUrl = "mod=getDepartMess&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "returnPerson: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void updateColl() {
        try{
            String httpUrl = "mod=updateColl&data="+URLEncoder.encode(""+datas.get("data"),"utf-8")
                    +"&info="+URLEncoder.encode(""+datas.get("info"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "updateColl: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void returnPerson() {
        try{
            String httpUrl = "mod=returnPerson&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "returnPerson: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void readed() {
        try{
            String httpUrl = "mod=readed&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "getMySign: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getMySign() {
        try{
            String httpUrl = "mod=getMySign&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "getMySign: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getMyColl() {
        try{
            String httpUrl = "mod=getMyColl&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "getMyColl: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void findAllUser() {
        try{
            String httpUrl = "mod=findAllUser";
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException e){
            Log.d(TAG, "findAllUser: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getMyMess() {
        try{
            String httpUrl = "mod=getMyMess&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "getMyMess: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void sendMess() {
        try{
            String httpUrl = "mod=sendMess&data="+URLEncoder.encode(""+datas.get("data"),"utf-8")
                    +"&type="+URLEncoder.encode(""+datas.get("type"),"utf-8");
            Log.d(TAG, "sendMess: "+URLEncoder.encode(""+datas.get("data"),"utf-8"));
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "sendMess: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void addSub() {
        try{
            String httpUrl = "mod=addSub&data="+URLEncoder.encode(""+datas.get("end"),"utf-8")
                    +"&quesId="+URLEncoder.encode(""+datas.get("id"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "qtaChange: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getAllQues() {
        try{
            String httpUrl = "mod=getAllQues";
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException e){
            Log.d(TAG, "qtaChange: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void qtaRe() {
        try{
            Gson gson = new Gson();
            String quesJson = gson.toJson(datas.get("ques"));
            String httpUrl = "mod=updateQTA&data="+URLEncoder.encode(""+quesJson,"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "qtaChange: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void qtaChange(){
        try{
            String httpUrl = "mod=qtaChange&data="+URLEncoder.encode(""+datas.get("department"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "qtaChange: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getDQues() {
        try{
            String httpUrl = "mod=getQues&data="+URLEncoder.encode(""+datas.get("department"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "getDQues: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void addQues() {
        try{
            String httpUrl = "mod=addQues&data="+URLEncoder.encode(""+datas.get("data"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "addQues: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getDepartment() {
        try{
            String httpUrl = "mod=getDepartment";
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException e){
            Log.d(TAG, "getDepartment: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getAllPay() {
        try{
            String httpUrl = "mod=getAllPay&data="+URLEncoder.encode(""+(Integer) datas.get("infoNo"),"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "changePwd: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void changePwd() {
        //FLAG = 1000
        try{
            Gson gson = new Gson();
            String newPwd = gson.toJson(datas.get("user"));
            String httpUrl = "mod=pwd&data="+ URLEncoder.encode(newPwd,"utf-8");
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException | UnsupportedEncodingException e){
            Log.d(TAG, "changePwd: NullPointerException or UnsupportedEncodingException");
        }
    }

    private void getAllVolu(){
        try{
            //String httpUrl = "mod=getAllVolu&data="+ URLEncoder.encode(""+(Integer) datas.get("infoNo"),"utf-8");
            String httpUrl = "mod=getAllVolu";
            httpUtils(httpUrl,FLAG);
        }catch (NullPointerException e){
            Log.d(TAG, "changePwd: NullPointerException or UnsupportedEncodingException");
        }
    }

    public void httpUtils(String url,int what){
        try {
            connection = HttpConnectionUtils.reGetConnection(url);
            int code = connection.getResponseCode();
            if (code == 200){
                InputStream inputStream = connection.getInputStream();
                String str = StreamChangeStrUtils.toChange(inputStream);//写个工具类流转换成字符串
                Message message = Message.obtain();//更新UI就要向消息机制发送消息
                message.what=what;//用来标志是哪个消息
                message.obj=str;//消息主体
                Log.d(TAG, "httpUtils: "+str);
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

    public void setFlag(int flag) {
        FLAG = flag;
    }
}
