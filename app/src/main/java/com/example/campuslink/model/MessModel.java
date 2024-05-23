package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessModel implements Message{

    String messTitle,messContent,messDepartment;
    String messId;
    String messTime;
    List<String> messScope,messList;

    public MessModel() {
    }

    public MessModel(String messTitle, String messContent, String messTime) {
        this.messTitle = messTitle;
        this.messContent = messContent;
        this.messTime = messTime;
    }

    //数据库
    public MessModel(String messTitle, String messContent, String messDepartment, String messId, String messTime, ArrayList<String> messScope, ArrayList<String> messList) {
        this.messTitle = messTitle;
        this.messContent = messContent;
        this.messDepartment = messDepartment;
        this.messId = messId;
        this.messTime = messTime;
        this.messScope = messScope;
        this.messList = messList;
    }

    public String getMessTitle() {
        return messTitle;
    }

    public void setMessTitle(String messTitle) {
        this.messTitle = messTitle;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }

    public String getMessDepartment() {
        return messDepartment;
    }

    public void setMessDepartment(String messDepartment) {
        this.messDepartment = messDepartment;
    }

    public String getMessId() {
        return messId;
    }

    public void setMessId(String messId) {
        this.messId = messId;
    }

    public String getMessTime() {
        return messTime;
    }

    public void setMessTime(String messTime) {
        this.messTime = messTime;
    }

    public List<String> getMessScope() {
        return messScope;
    }

    public void setMessScope(List<String> messScope) {
        this.messScope = messScope;
    }

    public List<String> getMessList() {
        return messList;
    }

    public void setMessList(List<String> messList) {
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
        return this.messTime;
    }

    @Override
    public List<String> getReadList() {
        return messList;
    }
}
