package com.example.campuslink.model;

public class DateTimeModel {

    private int year,month,day,hour,second;

    public DateTimeModel() {
    }

    public DateTimeModel(int year, int month, int day, int hour, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.second = second;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
