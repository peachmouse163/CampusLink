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

public class SignModel implements Message{

    //signId	signTitle	signContent	signDepartment	signTime	signScope	signList	signQuestions
    String signTitle,signContent,signDepartment;
    List<String> signScope,signList;
    List<Question> signQuestions;
    String signTime;
    String signId;

    public SignModel() {
    }

    //数据库


    public SignModel(String signTitle, String signContent, String signDepartment, List<String> signScope, List<String> signList, List<Question> signQuestions, String signTime, String signId) {
        this.signTitle = signTitle;
        this.signContent = signContent;
        this.signDepartment = signDepartment;
        this.signScope = signScope;
        this.signList = signList;
        this.signQuestions = signQuestions;
        this.signTime = signTime;
        this.signId = signId;
    }

    public String getSignTitle() {
        return signTitle;
    }

    public void setSignTitle(String signTitle) {
        this.signTitle = signTitle;
    }

    public String getSignContent() {
        return signContent;
    }

    public void setSignContent(String signContent) {
        this.signContent = signContent;
    }

    public String getSignDepartment() {
        return signDepartment;
    }

    public void setSignDepartment(String signDepartment) {
        this.signDepartment = signDepartment;
    }

    public List<String> getSignScope() {
        return signScope;
    }

    public void setSignScope(List<String> signScope) {
        this.signScope = signScope;
    }

    public List<String> getSignList() {
        return signList;
    }

    public void setSignList(List<String> signList) {
        this.signList = signList;
    }

    public List<Question> getSignQuestions() {
        return signQuestions;
    }

    public void setSignQuestions(List<Question> signQuestions) {
        this.signQuestions = signQuestions;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
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
        return this.signTime;
        /*SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(this.signTime);*/
    }

    @Override
    public List<String> getReadList() {
        return signList;
    }
}
