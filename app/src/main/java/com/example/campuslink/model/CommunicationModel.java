package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.List;

public class CommunicationModel implements Message{

    String comNo;
    String comDatetime,infoNoA,infoNoB,comContent;

    public CommunicationModel() {
    }

    public CommunicationModel(String comNo, String comDatetime, String infoNoA, String infoNoB, String comContent) {
        this.comNo = comNo;
        this.comDatetime = comDatetime;
        this.infoNoA = infoNoA;
        this.infoNoB = infoNoB;
        this.comContent = comContent;
    }

    public String getComNo() {
        return comNo;
    }

    public void setComNo(String comNo) {
        this.comNo = comNo;
    }

    public String getComDatetime() {
        return comDatetime;
    }

    public void setComDatetime(String comDatetime) {
        this.comDatetime = comDatetime;
    }

    public String getInfoNoA() {
        return infoNoA;
    }

    public void setInfoNoA(String infoNoA) {
        this.infoNoA = infoNoA;
    }

    public String getInfoNoB() {
        return infoNoB;
    }

    public void setInfoNoB(String infoNoB) {
        this.infoNoB = infoNoB;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    @Override
    public String getTitle() {
        return this.infoNoB;
    }

    @Override
    public String getMessage() {
        return this.comContent;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_hello;
    }

    @Override
    public String getTime() {
        return this.comDatetime;
    }

    @Override
    public List<String> getReadList() {
        return null;
    }

    @Override
    public String toString() {
        return "CommunicationModel{" +
                "comNo='" + comNo + '\'' +
                ", comDatetime='" + comDatetime + '\'' +
                ", infoNoA='" + infoNoA + '\'' +
                ", infoNoB='" + infoNoB + '\'' +
                ", comContent='" + comContent + '\'' +
                '}';
    }
}
