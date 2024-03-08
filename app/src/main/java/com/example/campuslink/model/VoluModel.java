package com.example.campuslink.model;

public class VoluModel implements Preview {

    //voluId	voluTitle	voluTime	infoNo	voluNum	voluOnline	voluPlace	voluContent	voluStart	voluEnd	voluPoint

    char[] voluId;
    String voluTitle,voluPlace,voluContent;
    DateTimeModel voluTime,voluStart,voluEnd;
    int infoNo,voluOnline,voluNum,voluPoint;
    //0——线下；
    //1——线上


    public VoluModel() {
    }

    public VoluModel(char[] voluId, String voluTitle, String voluPlace, String voluContent, DateTimeModel voluTime, DateTimeModel voluStart, DateTimeModel voluEnd, int infoNo, int voluOnline, int voluNum, int voluPoint) {
        this.voluId = voluId;
        this.voluTitle = voluTitle;
        this.voluPlace = voluPlace;
        this.voluContent = voluContent;
        this.voluTime = voluTime;
        this.voluStart = voluStart;
        this.voluEnd = voluEnd;
        this.infoNo = infoNo;
        this.voluOnline = voluOnline;
        this.voluNum = voluNum;
        this.voluPoint = voluPoint;
    }


}
