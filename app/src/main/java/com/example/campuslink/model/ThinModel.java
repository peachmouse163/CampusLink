package com.example.campuslink.model;

import android.util.Log;

public class ThinModel implements Preview {

    /*thinNo
thinTitle
thinDate
infoNo
thinAttribute
thinPlace
thinPhone
thinPic
thinContent
thinState
*/

    private final String TAG = "ThinModel";

    String thinNo,thinTitle,thinDate,infoNo,thinAttribute,thinPlace,thinPhone,thinPic,thinContent,thinState;

    public ThinModel() {
    }

    public ThinModel(String thinNo,String thinTitle,String thinDate,String infoNo,String thinAttribute,String thinPlace,String thinPhone,String thinPic,String thinContent,String thinState) {
        this.thinNo = thinNo;
        this.thinTitle = thinTitle;
        this.thinPlace = thinPlace;
        this.thinPic = thinPic;
        this.thinContent = thinContent;
        this.thinDate = thinDate;
        this.infoNo = infoNo;

        Log.d(TAG, "ThinModel: "+thinAttribute);
        this.thinAttribute = thinAttribute;
        this.thinPhone = thinPhone;
        this.thinState = thinState;
    }


    public String getThinNo() {
        return thinNo;
    }

    public void setThinNo(String thinNo) {
        this.thinNo = thinNo;
    }

    public String getThinTitle() {
        return thinTitle;
    }

    public void setThinTitle(String thinTitle) {
        this.thinTitle = thinTitle;
    }

    public String getThinDate() {
        return thinDate;
    }

    public void setThinDate(String thinDate) {
        this.thinDate = thinDate;
    }

    public String getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(String infoNo) {
        this.infoNo = infoNo;
    }

    public String getThinAttribute() {
        return thinAttribute;
    }

    public void setThinAttribute(String thinAttribute) {
        this.thinAttribute = thinAttribute;
    }

    public String getThinPlace() {
        return thinPlace;
    }

    public void setThinPlace(String thinPlace) {
        this.thinPlace = thinPlace;
    }

    public String getThinPhone() {
        return thinPhone;
    }

    public void setThinPhone(String thinPhone) {
        this.thinPhone = thinPhone;
    }

    public String getThinPic() {
        return thinPic;
    }

    public void setThinPic(String thinPic) {
        this.thinPic = thinPic;
    }

    public String getThinContent() {
        return thinContent;
    }

    public void setThinContent(String thinContent) {
        this.thinContent = thinContent;
    }

    public String getThinState() {
        return thinState;
    }

    public void setThinState(String thinState) {
        this.thinState = thinState;
    }

    @Override
    public String getTitle() {
        return this.thinTitle;
    }

    @Override
    public String getDateTime() {
        return this.thinDate;
    }

    @Override
    public String getInfoName() {
        return "编号:"+this.infoNo;
    }
}
