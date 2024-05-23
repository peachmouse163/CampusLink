package com.example.campuslink.model;

import com.example.campuslink.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CollectModel implements Message{
    //collId	collTitle	collContent	collDepartment	collTime	collScope	collQuestions
    String collTitle,collContent,collDepartment;
    String collId;
    List<String> collScope,collList;
    List<Question> collQuestions;
    String collTime;

    public CollectModel() {
    }

    public CollectModel(String collTitle, String collContent, String collDepartment, String collId, List<String> collScope, List<String> collList, List<Question> collQuestions, String collTime) {
        this.collTitle = collTitle;
        this.collContent = collContent;
        this.collDepartment = collDepartment;
        this.collId = collId;
        this.collScope = collScope;
        this.collList = collList;
        this.collQuestions = collQuestions;
        this.collTime = collTime;
    }

    public String getCollTitle() {
        return collTitle;
    }

    public void setCollTitle(String collTitle) {
        this.collTitle = collTitle;
    }

    public String getCollContent() {
        return collContent;
    }

    public void setCollContent(String collContent) {
        this.collContent = collContent;
    }

    public String getCollDepartment() {
        return collDepartment;
    }

    public void setCollDepartment(String collDepartment) {
        this.collDepartment = collDepartment;
    }

    public String getCollId() {
        return collId;
    }

    public void setCollId(String collId) {
        this.collId = collId;
    }

    public List<String> getCollScope() {
        return collScope;
    }

    public void setCollScope(List<String> collScope) {
        this.collScope = collScope;
    }

    public List<String> getCollList() {
        return collList;
    }

    public void setCollList(List<String> collList) {
        this.collList = collList;
    }

    public List<Question> getCollQuestions() {
        return collQuestions;
    }

    public void setCollQuestions(List<Question> collQuestions) {
        this.collQuestions = collQuestions;
    }

    public String getCollTime() {
        return collTime;
    }

    public void setCollTime(String collTime) {
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
        /*SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(this.collTime);*/
        return this.collTime;
    }

    @Override
    public List<String> getReadList() {
        return collList;
    }
}
