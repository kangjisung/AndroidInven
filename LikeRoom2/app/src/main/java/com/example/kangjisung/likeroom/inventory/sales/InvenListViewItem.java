package com.example.kangjisung.likeroom.inventory.sales;

/**
 * Created by kangjisung on 2016-11-21.
 */

public class InvenListViewItem {

    private String nameStr ;
    private String todayStr ;
    private String salesVolumeStr;


    public void setBname(String Bname) {
        nameStr = Bname ;
    }
    public void settoday(String today) {
        todayStr = today ;
    }
    public void setsales(String sales) {
        salesVolumeStr = sales;
    }


    public String getBname() {
        return this.nameStr ;
    }
    public String gettoday() {
        return this.todayStr ;
    }
    public String getsales() {
        return this.salesVolumeStr;
    }
}
