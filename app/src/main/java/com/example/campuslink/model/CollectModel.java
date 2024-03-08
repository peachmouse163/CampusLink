package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.ArrayList;

public class CollectModel implements Message{
    //collId	collTitle	collContent	collDepartment	collTime	collScope	collQuestions
    String collTitle,collContent,collDepartment;
    char[] collId;
    ArrayList<String> collScope,collQuestions;
    DateTimeModel collTime;

    public CollectModel() {
        this.collContent = Message.message;
        this.collTitle = Message.title;
        this.collTime = new DateTimeModel().setDateTime(Message.time);
    }

    //数据库
    public CollectModel(String collTitle, String collContent, String collDepartment, char[] collId, ArrayList<String> collScope, ArrayList<String> collQuestions, DateTimeModel collTime) {
        this.collTitle = collTitle;
        this.collContent = collContent;
        this.collDepartment = collDepartment;
        this.collId = collId;
        this.collScope = collScope;
        this.collQuestions = collQuestions;
        this.collTime = collTime;
    }

    @Override
    public String getTitle() {
        return this.collTitle;
    }

    @Override
    public String getMessage() {
        return this.collContent;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_star;
    }

    @Override
    public String getTime() {
        return this.collTime.getDateTime();
    }
}
