package com.example.campuslink.model;

import com.google.gson.annotations.Expose;

public class User {

    String infoName,infoDepartment,infoRefresh,infoPhone;
    int infoIdentity;
    String infoZy,infoYear,infoClass;

    @Expose(serialize = true,deserialize = true)
    int infoNo;
    @Expose(serialize = true,deserialize = true)
    String infoPassword;



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

    public String getInfoPhone() {
        return infoPhone;
    }

    public void setInfoPhone(String infoPhone) {
        this.infoPhone = infoPhone;
    }

    public int getInfoIdentity() {
        return infoIdentity;
    }

    public void setInfoIdentity(int infoIdentity) {
        this.infoIdentity = infoIdentity;
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

    public int getInfoNo() {
        return infoNo;
    }

    public void setInfoNo(int infoNo) {
        this.infoNo = infoNo;
    }

    public String getInfoPassword() {
        return infoPassword;
    }

    public void setInfoPassword(String infoPassword) {
        this.infoPassword = infoPassword;
    }
}
