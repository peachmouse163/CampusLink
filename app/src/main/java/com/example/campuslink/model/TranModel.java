package com.example.campuslink.model;

import com.example.campuslink.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TranModel implements Preview {

    //tranId infoNo tranMoney tranPlace tranDatetime tranPic tranTitle tranContent tranState

    private String tranId ,infoNo   ,tranPlace ,   tranTitle ,tranContent ,tranState;
    private int tranPic ,tranMoney;
    private Date tranDatetime;

    public TranModel(String tranId, String infoNo,String tranMoney, String tranPlace,  String tranDatetime, String tranPic,String tranTitle, String tranContent, String tranState) {
        this.tranId = tranId;
        this.infoNo = infoNo;
        this.tranPlace = tranPlace;
        this.tranTitle = tranTitle;
        this.tranContent = tranContent;
        this.tranState = tranState;
        setTranPic(tranPic);
        this.tranMoney = Integer.parseInt(tranMoney);
        setTranDatetime(tranDatetime);
    }

    public TranModel() {
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(String infoNo) {
        this.infoNo = infoNo;
    }

    public String getTranPlace() {
        return tranPlace;
    }

    public void setTranPlace(String tranPlace) {
        this.tranPlace = tranPlace;
    }

    public String getTranTitle() {
        return tranTitle;
    }

    public void setTranTitle(String tranTitle) {
        this.tranTitle = tranTitle;
    }

    public String getTranContent() {
        return tranContent;
    }

    public void setTranContent(String tranContent) {
        this.tranContent = tranContent;
    }

    public String getTranState() {
        return tranState;
    }

    public void setTranState(String tranState) {
        this.tranState = tranState;
    }

    public int getTranPic() {
        return tranPic;
    }

    public void setTranPic(String tranPic) {
        try{
            this.tranPic = Integer.parseInt(tranPic);
        }catch (Exception e){
            e.printStackTrace();
            this.tranPic = R.drawable.ic_pic;
        }

    }

    public int getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(int tranMoney) {
        this.tranMoney = tranMoney;
    }

    public String getTranDatetime() {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(this.tranDatetime);
    }

    public void setTranDatetime(String tranDatetime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        try {
            this.tranDatetime = dateFormat.parse(tranDatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.tranDatetime = new Date();
    }

    @Override
    public String getTitle() {
        return this.tranTitle;
    }

    @Override
    public String getDateTime() {
        return getTranDatetime();
    }

    @Override
    public String getInfoName() {
        return this.infoNo;
    }
}
