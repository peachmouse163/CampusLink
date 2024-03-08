package com.example.campuslink.model;

import com.example.campuslink.R;

public interface Message {

    String title = "标题",message="内容",time="2024年6月20日12：30";
    int pic = R.drawable.ic_star;

    String getTitle();
    String getMessage();
    int getPic();
    String getTime();

}
