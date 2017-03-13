package com.teamdk.android.bakery.objectmanager;

import java.util.Date;

public class NoticeListItem
{
    private int num;
    private String title;
    private String body;
    private Date startDate;
    private Date endDate;
    private Date makeDate;
    private int type;
    private int delete;
    private int close;

    public NoticeListItem(){}
    public NoticeListItem(int num, String title, String body, Date startDate, Date endDate, Date makeDate, int type, int delete, int close)
    {
        this.num = num;
        this.title = title;
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
        this.makeDate = makeDate;
        this.type = type;
        this.delete = delete;
        this.close = close;
    }

    public void setNum(int num) {this.num = num;}
    public void setTitle(String title) {this.title = title;}
    public void setBody(String body) {this.body = body;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
    public void setMakeDate(Date makeDate) {this.makeDate = makeDate;}
    public void setType(int type) {this.type = type;}
    public void setDelete(int delete) {this.delete = delete;}
    public void setClose(int close) {this.close = close;}

    public int getNum() {return this.num;}
    public String getTitle() {return this.title;}
    public String getBody() {return this.body;}
    public Date getStartDate() {return this.startDate;}
    public Date getEndDate() {return this.endDate;}
    public Date getMakeDate() {return this.makeDate;}
    public int getType() {return this.type;}
    public int getDelete() {return this.delete;}
    public int getClose() {return this.close;}
}