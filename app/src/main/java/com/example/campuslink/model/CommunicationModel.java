package com.example.campuslink.model;

import com.example.campuslink.R;

public class CommunicationModel implements Message{

    String infoName,message;
    int infoPic;
    DateTimeModel time;
    String infoAnother;

    public CommunicationModel() {
        this.infoName = Message.title;
        this.infoPic = R.drawable.ic_hello;
        this.message = Message.message;
        this.time = new DateTimeModel().setDateTime(Message.time);
    }

    //数据库的构造方法
    public CommunicationModel(String infoName, String message, String time, String infoAnother) {
        this.infoName = infoName;
        this.message = message;
        this.time = new DateTimeModel().setDateTime(time);
        this.infoAnother = infoAnother;
        //不含头像，额外查数据库
        this.infoPic = Message.pic;
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
        return this.time.getDateTime();
    }
}
