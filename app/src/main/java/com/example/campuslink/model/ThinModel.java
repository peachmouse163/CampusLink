package com.example.campuslink.model;

public class ThinModel implements Preview {

    //thinNo	thinTitle	thinDate	infoNo	thinAttribute	thinPlace	thinPhone	thinPic	thinContent
    char[] thinNo;
    String thinTitle,thinPlace,thinPic,thinContent;
    DateTimeModel thinDate = new DateTimeModel();
    int infoNo,thinAttribute,thinPhone;

    public ThinModel() {
    }

    public ThinModel(char[] thinNo, String thinTitle, String thinPlace, String thinPic, String thinContent, DateTimeModel thinDate, int infoNo, int thinAttribute, int thinPhone) {
        this.thinNo = thinNo;
        this.thinTitle = thinTitle;
        this.thinPlace = thinPlace;
        this.thinPic = thinPic;
        this.thinContent = thinContent;
        this.thinDate = thinDate;
        this.infoNo = infoNo;
        this.thinAttribute = thinAttribute;
        this.thinPhone = thinPhone;
    }

    public char[] getThinNo() {
        return thinNo;
    }

    public void setThinNo(char[] thinNo) {
        if (thinNo[0] != 'T')
            return;
        this.thinNo = thinNo;
    }

    public String getThinTitle() {
        return thinTitle;
    }

    public void setThinTitle(String thinTitle) {
        this.thinTitle = thinTitle;
    }

    public String getThinPlace() {
        return thinPlace;
    }

    public void setThinPlace(String thinPlace) {
        this.thinPlace = thinPlace;
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

    public DateTimeModel getThinDate() {
        return thinDate;
    }

    public void setThinDate(DateTimeModel thinDate) {
        this.thinDate = thinDate;
    }

    public int getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(int infoNo) {
        if (new StringBuffer(infoNo).length() > 12)
            return;
        this.infoNo = infoNo;
    }

    public int getThinAttribute() {
        return thinAttribute;
    }

    public void setThinAttribute(int thinAttribute) {
        //0丢
        //1捡
        if (thinAttribute != 0 && thinAttribute != 1)
            return;
        this.thinAttribute = thinAttribute;
    }

    public int getThinPhone() {
        return thinPhone;
    }

    public void setThinPhone(int thinPhone) {
        if (new StringBuilder(thinPhone).length() > 13)
            return;
        this.thinPhone = thinPhone;
    }
}
