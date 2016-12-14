package com.example.kangjisung.likeroom.inventory.InvenList;

/**
 * Created by kangjisung on 2016-11-21.
 */

public class InvenListViewItem {

    private String nameStr ;  //이름
    private String todayStr ;  //최신등록일
    private String salesVolumeStr;  //판매량 및 최적재고량


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
