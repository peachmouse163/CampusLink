package com.example.campuslink.model;

public class User {

    private String infoName,infoDepartment,infoRefresh,infoPassword,infoPhone;
    public static int infoNo,infoIdentity;
    //
    private String infoZy,infoYear,infoClass;



    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoDepartment() {
        return infoDepartment;
    }

    public void setInfoDepartment(String infoDepartment) {
        this.infoDepartment = infoDepartment;
    }

    public String getInfoRefresh() {
        return infoRefresh;
    }

    public void setInfoRefresh(String infoRefresh) {
        this.infoRefresh = infoRefresh;
    }

    public String getInfoPassword() {
        return infoPassword;
    }

    public void setInfoPassword(String infoPassword) {
        this.infoPassword = infoPassword;
    }

    public int getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(int infoNo) {
        this.infoNo = infoNo;
    }

    public String getInfoPhone() {
        return infoPhone;
    }

    public void setInfoPhone(String infoPhone) {
        this.infoPhone = infoPhone;
    }

    public String getInfoZy() {
        return infoZy;
    }

    public void setInfoZy(String infoZy) {
        this.infoZy = infoZy;
    }

    public String getInfoYear() {
        return infoYear;
    }

    public void setInfoYear(String infoYear) {
        this.infoYear = infoYear;
    }

    public String getInfoClass() {
        return infoClass;
    }

    public void setInfoClass(String infoClass) {
        this.infoClass = infoClass;
    }

    public static int getInfoIdentity() {
        return infoIdentity;
    }

    public static void setInfoIdentity(int infoIdentity) {
        User.infoIdentity = infoIdentity;
    }
}
