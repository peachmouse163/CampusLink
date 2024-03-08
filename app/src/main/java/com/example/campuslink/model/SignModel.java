package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.ArrayList;

public class SignModel implements Message{

    //signId	signTitle	signContent	signDepartment	signTime	signScope	signList	signQuestions
    String signTitle,signContent,signDepartment;
    ArrayList<String> signScope,signList,signQuestions;
    DateTimeModel signTime;
    char[] signId;

    public SignModel() {
        this.signContent = Message.message;
        this.signTitle = Message.title;
        this.signTime = new DateTimeModel().setDateTime(Message.time);
    }

    //数据库
    public SignModel(String signTitle, String signContent, String signDepartment, ArrayList<String> signScope, ArrayList<String> signList, ArrayList<String> signQuestions, DateTimeModel signTime, char[] signId) {
        this.signTitle = signTitle;
        this.signContent = signContent;
        this.signDepartment = signDepartment;
        this.signScope = signScope;
        this.signList = signList;
        this.signQuestions = signQuestions;
        this.signTime = signTime;
        this.signId = signId;
    }


    @Override
    public String getTitle() {
        return this.signTitle;
    }

    @Override
    public String getMessage() {
        return this.signContent;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_star;
    }

    @Override
    public String getTime() {
        return this.signTime.getDateTime();
    }
}
