package com.example.campuslink.model;

import com.example.campuslink.R;

import java.util.List;

public class QuesToAnModel implements Message {
    //quesId
    //infoNoA
    //quesTitle
    //quesContentA
    //quesTimeA
    //infoNoB
    //quesTimeB
    //quesContentB
    //quesEnd
    String quesId,infoNoA,quesTitle,quesContentA,quesTimeA,infoNoB,quesTimeB,quesContentB,quesEnd;

    public QuesToAnModel(){

    }

    public QuesToAnModel(String quesTitle) {
        this.quesTitle = quesTitle;
    }

    public QuesToAnModel(String quesId, String infoNoA, String quesTitle, String quesContentA, String quesTimeA, String infoNoB, String quesTimeB, String quesContentB, String quesEnd) {
        this.quesId = quesId;
        this.infoNoA = infoNoA;
        this.quesTitle = quesTitle;
        this.quesContentA = quesContentA;
        this.quesTimeA = quesTimeA;
        this.infoNoB = infoNoB;
        this.quesTimeB = quesTimeB;
        this.quesContentB = quesContentB;
        this.quesEnd = quesEnd;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getInfoNoA() {
        return infoNoA;
    }

    public void setInfoNoA(String infoNoA) {
        this.infoNoA = infoNoA;
    }

    public String getQuesTitle() {
        return quesTitle;
    }

    public void setQuesTitle(String quesTitle) {
        this.quesTitle = quesTitle;
    }

    public String getQuesContentA() {
        return quesContentA;
    }

    public void setQuesContentA(String quesContentA) {
        this.quesContentA = quesContentA;
    }

    public String getQuesTimeA() {
        return quesTimeA;
    }

    public void setQuesTimeA(String quesTimeA) {
        this.quesTimeA = quesTimeA;
    }

    public String getInfoNoB() {
        return infoNoB;
    }

    public void setInfoNoB(String infoNoB) {
        this.infoNoB = infoNoB;
    }

    public String getQuesTimeB() {
        return quesTimeB;
    }

    public void setQuesTimeB(String quesTimeB) {
        this.quesTimeB = quesTimeB;
    }

    public String getQuesContentB() {
        return quesContentB;
    }

    public void setQuesContentB(String quesContentB) {
        this.quesContentB = quesContentB;
    }

    public String getQuesEnd() {
        return quesEnd;
    }

    public void setQuesEnd(String quesEnd) {
        this.quesEnd = quesEnd;
    }

    @Override
    public String getTitle() {
        return this.quesTitle;
    }

    @Override
    public String getMessage() {
        return this.quesContentA;
    }

    @Override
    public int getPic() {
        return R.drawable.ic_star;
    }

    @Override
    public String getTime() {
        return this.quesTimeA;
    }

    @Override
    public List<String> getReadList() {
        return null;
    }
}
