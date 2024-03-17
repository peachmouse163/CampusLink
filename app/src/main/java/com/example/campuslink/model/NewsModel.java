package com.example.campuslink.model;

public class NewsModel {

    /*	newId
	newTitle
	newTime
	newDepartment
	infoNo
	newContent
	newUrl
 */
    private String newId,newTitle,newTime,newDepartment,infoNo,newContent,newUrl;

    public NewsModel(String newId, String newTitle, String newTime, String newDepartment, String infoNo, String newContent,
                String newUrl) {
        super();
        this.newId = newId;
        this.newTitle = newTitle;
        this.newTime = newTime;
        this.newDepartment = newDepartment;
        this.infoNo = infoNo;
        this.newContent = newContent;
        this.newUrl = newUrl;
    }
    public NewsModel(){

    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTime() {
        return newTime;
    }

    public void setNewTime(String newTime) {
        this.newTime = newTime;
    }

    public String getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(String newDepartment) {
        this.newDepartment = newDepartment;
    }

    public String getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(String infoNo) {
        this.infoNo = infoNo;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }
}
