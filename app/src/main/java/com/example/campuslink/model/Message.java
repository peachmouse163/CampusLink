package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.ArrayList;
import java.util.List;

public interface Message {

    String title = "标题",message="内容",time="2024年6月20日12：30";
    String id = "id",department="部门";
    ArrayList<String> scope=null,list=null;
    int pic = R.drawable.ic_star;

    public String getTitle();

    public String getMessage();

    public int getPic();

    public String getTime();

    public List<String> getReadList();

}
