package com.example.campuslink.model;

import com.example.campuslink.R;

public interface Preview {

    String title="这是标题"
            ,infoName="我是人名"
            ,dateTime = "时间在这里";
    int pic= R.drawable.ic_pic
            ,art=R.drawable.ic_star;

    public String getTitle();
    public String getDateTime();
    public String getInfoName();

}
