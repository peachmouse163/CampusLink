package com.example.campuslink.model;

import androidx.annotation.Nullable;

public class VoluModel implements Preview {

    /*
    voluId
    voluTitle
    voluTime
    infoNo
    voluNum
    voluOnline
    voluPlace
    voluContent
    voluStart
    voluEnd
    voluPoint
    voluState
    */

    String voluId ,voluTitle ,voluTime ,infoNo ,voluNum ,voluOnline ,voluPlace ,voluContent ,voluStart ,voluEnd ,voluPoint ,voluState;
    //voluOnline——>0——线下；1——线上
    //voluState——>0未完成


    public VoluModel() {
    }

    public VoluModel(String voluId, String voluTitle, String voluTime, String infoNo, String voluNum, String voluOnline, String voluPlace, String voluContent, String voluStart, String voluEnd, String voluPoint, String voluState) {
        this.voluId = voluId;
        this.voluTitle = voluTitle;
        this.voluTime = voluTime;
        this.infoNo = infoNo;
        this.voluNum = voluNum;
        this.voluOnline = voluOnline;
        this.voluPlace = voluPlace;
        this.voluContent = voluContent;
        this.voluStart = voluStart;
        this.voluEnd = voluEnd;
        this.voluPoint = voluPoint;
        this.voluState = voluState;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return ((VoluModel) obj).getVoluId().equals(this.getVoluId());
    }

    public String getVoluId() {
        return voluId;
    }

    public void setVoluId(String voluId) {
        this.voluId = voluId;
    }

    public String getVoluTitle() {
        return voluTitle;
    }

    public void setVoluTitle(String voluTitle) {
        this.voluTitle = voluTitle;
    }

    public String getVoluTime() {
        return voluTime;
    }

    public void setVoluTime(String voluTime) {
        this.voluTime = voluTime;
    }

    public String getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(String infoNo) {
        this.infoNo = infoNo;
    }

    public String getVoluNum() {
        return voluNum;
    }

    public void setVoluNum(String voluNum) {
        this.voluNum = voluNum;
    }

    public String getVoluOnline() {
        return voluOnline;
    }

    public void setVoluOnline(String voluOnline) {
        this.voluOnline = voluOnline;
    }

    public String getVoluPlace() {
        return voluPlace;
    }

    public void setVoluPlace(String voluPlace) {
        this.voluPlace = voluPlace;
    }

    public String getVoluContent() {
        return voluContent;
    }

    public void setVoluContent(String voluContent) {
        this.voluContent = voluContent;
    }

    public String getVoluStart() {
        return voluStart;
    }

    public void setVoluStart(String voluStart) {
        this.voluStart = voluStart;
    }

    public String getVoluEnd() {
        return voluEnd;
    }

    public void setVoluEnd(String voluEnd) {
        this.voluEnd = voluEnd;
    }

    public String getVoluPoint() {
        return voluPoint;
    }

    public void setVoluPoint(String voluPoint) {
        this.voluPoint = voluPoint;
    }

    public String getVoluState() {
        return voluState;
    }

    public void setVoluState(String voluState) {
        this.voluState = voluState;
    }

    @Override
    public String getTitle() {
        return voluTitle;
    }

    @Override
    public String getDateTime() {
        return voluTime;
    }

    @Override
    public String getInfoName() {
        return "编号:"+infoNo;
    }
}
