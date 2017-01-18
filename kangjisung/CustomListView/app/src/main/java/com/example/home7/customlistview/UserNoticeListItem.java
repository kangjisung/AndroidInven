package com.example.home7.customlistview;

import java.util.Date;

/**
 * Created by kangjisung on 2017-01-16.
 */

public class UserNoticeListItem {

    private String title;
    private String body;
    private Date startDate;
    private Date endDate;
    private int type;

    public void setTitle(String _title) {title = _title;}
    public void setBody(String _body) {body = _body;}
    public void setStartDate(Date _startDate) {startDate = _startDate;}
    public void setEndDate(Date _endDate) {endDate = _endDate;}
    public void setType(int _type) {type = _type;}

    public String getTitle() {return this.title;}
    public String getBody() {return this.body;}
    public Date getStartDate() {return this.startDate;}
    public Date getEndDate() {return this.endDate;}
    public int getType() {return this.type;}
}
