package com.example.kangjisung.likeroom.FragmentUser.ListView;

import java.util.Date;

public class UserNoticeListItem
{
    private String title;
    private String body;
    private Date startDate;
    private Date endDate;
    private int type;
    private int close;

    UserNoticeListItem(){}
    public UserNoticeListItem(String title, String body, Date startDate, Date endDate, int type, int close)
    {
        this.title = title;
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.close = close;
    }

    public void setTitle(String _title) {title = _title;}
    public void setBody(String _body) {body = _body;}
    public void setStartDate(Date _startDate) {startDate = _startDate;}
    public void setEndDate(Date _endDate) {endDate = _endDate;}
    public void setType(int _type) {type = _type;}
    public void setClose(int _close) {close = _close;}

    public String getTitle() {return this.title;}
    public String getBody() {return this.body;}
    public Date getStartDate() {return this.startDate;}
    public Date getEndDate() {return this.endDate;}
    public int getType() {return this.type;}
    public int getClose() {return this.close;}
}