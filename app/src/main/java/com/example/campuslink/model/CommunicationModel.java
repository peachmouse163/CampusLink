package com.example.campuslink.model;

import com.example.campuslink.R;

public class CommunicationModel implements Message{

    String infoName,message,comNo;
    int infoPic;
    String time;
    String infoAnother;

    public CommunicationModel() {
        this.infoName = Message.title;
        this.infoPic = R.drawable.ic_hello;
        this.message = Message.message;
        this.time = "";
    }

    //数据库的构造方法
    public CommunicationModel(String comNo, String time,String infoName, String infoAnother, String message) {
        this.comNo = comNo;
        this.time = time;
        this.infoName = infoName;
        this.infoAnother = infoAnother;
        this.message = message;

        //不含头像，额外查数据库
        this.infoPic = R.drawable.ic_hello;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setComNo(String comNo) {
        this.comNo = comNo;
    }

    public void setInfoPic(int infoPic) {
        this.infoPic = infoPic;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInfoAnother(String infoAnother) {
        this.infoAnother = infoAnother;
    }

    public String getInfoAnother() {
        return infoAnother;
    }

    @Override
    public String getTitle() {
        return this.infoName;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_hello;
    }

    @Override
    public String getTime() {
        return time;
    }
}
