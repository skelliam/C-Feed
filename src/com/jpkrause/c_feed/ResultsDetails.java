package com.jpkrause.c_feed;

public class ResultsDetails {
    private int icon ;
    private String title;
    private String desc;
    private String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String from) {
        this.title = from;
    }
   
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
   
    public String getDate() {
        return date;
    }

    public void setDate(String time) {
        this.date = time;
    }
   
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
