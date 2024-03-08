package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.ArrayList;

public class MessModel implements Message{

    String messTitle,messContent,messDepartment;
    char[] messId;
    DateTimeModel messTime;
    ArrayList<String> messScope,messList;


    public MessModel() {
        this.messTitle = Message.title;
        this.messTime = new DateTimeModel().setDateTime(Message.time);
        this.messContent = Message.message;
    }

    //数据库
    public MessModel(String messTitle, String messContent, String messDepartment, char[] messId, DateTimeModel messTime, ArrayList<String> messScope, ArrayList<String> messList) {
        this.messTitle = messTitle;
        this.messContent = messContent;
        this.messDepartment = messDepartment;
        this.messId = messId;
        this.messTime = messTime;
        this.messScope = messScope;
        this.messList = messList;
    }

    @Override
    public String getTitle() {
        return this.messTitle;
    }

    @Override
    public String getMessage() {
        return this.messContent;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_star;
    }

    @Override
    public String getTime() {
        return this.messTime.getDateTime();
    }
}
