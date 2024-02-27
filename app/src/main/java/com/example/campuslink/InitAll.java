package com.example.campuslink;

public interface InitAll {

    //隐藏标题栏
    public void HideTitle();

    //用于所有App的初始化绑定+数据初始化+点击响应
    public void initView();
    public void initData();
    public void initClick();

}
