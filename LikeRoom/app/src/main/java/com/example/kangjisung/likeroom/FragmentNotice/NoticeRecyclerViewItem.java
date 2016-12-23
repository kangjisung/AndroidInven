package com.example.kangjisung.likeroom.FragmentNotice;

import java.util.Calendar;

public class NoticeRecyclerViewItem
{
    private String title;
    private String body;
    private Calendar startDate;
    private Calendar endDate;
    private int type;

    public void setTitle(String _title) {title = _title;}
    public void setBody(String _body) {body = _body;}
    public void setStartDate(Calendar _startDate) {startDate = _startDate;}
    public void setEndDate(Calendar _endDate) {endDate = _endDate;}
    public void setType(int _type) {type = _type;}

    public String getTitle() {return this.title;}
    public String getBody() {return this.body;}
    public Calendar getStartDate() {return this.startDate;}
    public Calendar getEndDate() {return this.endDate;}
    public int getType() {return this.type;}
}