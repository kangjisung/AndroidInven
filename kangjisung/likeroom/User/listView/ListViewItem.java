package com.example.kangjisung.likeroom.User.listView;

/**
 * Created by kangjisung on 2016-11-30.
 */

public class ListViewItem {

    private String nameStr ;
    private String phoneStr ;
    private String pointStr;


    public void setTitle(String title) {
        nameStr = title ;
    }
    public void setDesc(String desc) {
        phoneStr = desc ;
    }
    public void setpoint(String point){pointStr=point;}

    public String getTitle() {
        return this.nameStr ;
    }
    public String getDesc() {
        return this.phoneStr ;
    }
    public String getpoint(){return this.pointStr;}
}
