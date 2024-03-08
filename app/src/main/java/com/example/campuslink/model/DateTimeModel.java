package com.example.campuslink.model;

public class DateTimeModel {

    private int year,month,day,hour,second;
    private final String YEAR="年",MONTH="月",DAY="日",HOUR="：";

    public DateTimeModel() {
    }

    public DateTimeModel(int year, int month, int day, int hour, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.second = second;
    }

    public DateTimeModel setDateTime(String s){
        StringBuffer buffer = new StringBuffer(s);
        //      2022 a 12 b 30 c  12  d  30
        int a,b,c,d,end;
        a = buffer.indexOf(YEAR);
        b = buffer.indexOf(MONTH);
        c = buffer.indexOf(DAY);
        d = buffer.indexOf(HOUR);
        end = buffer.length();

        this.year = Integer.parseInt(buffer.subSequence(1,a).toString());
        this.month = Integer.parseInt(buffer.subSequence(a+1,b).toString());
        this.day = Integer.parseInt(buffer.subSequence(b+1,c).toString());
        this.hour = Integer.parseInt(buffer.subSequence(c+1,d).toString());
        this.second = Integer.parseInt(buffer.subSequence(d+1,end).toString());

        return this;
    }

    public String getDateTime(DateTimeModel model){
        return model.getYear()+"年"+ model.getMonth()+"月"+model.getDay()+"日"+model.getHour()+"："+model.getSecond();
    }
    public String getDateTime(){
        return this.getYear()+"年"+ this.getMonth()+"月"+this.getDay()+"日"+this.getHour()+"："+this.getSecond();
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
