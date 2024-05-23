package com.example.campuslink.model;

import java.util.Objects;

public class Question {

    /*type:
    填空：0
    单选：1
    多选：2
    时间：3
    */
    String type,question,answer;

    public Question() {
    }

    public Question(String type, String question) {
        this.type = type;
        this.question = question;
        this.answer = "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question1 = (Question) o;
        return Objects.equals(type, question1.type) && Objects.equals(question, question1.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, question);
    }
}
