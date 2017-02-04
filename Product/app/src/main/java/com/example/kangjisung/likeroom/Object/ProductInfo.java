package com.example.kangjisung.likeroom.Object;

import java.util.Date;

/**
 * Created by user on 2016-12-15.
 */

public class ProductInfo {
    private String name;
    private boolean isStar;
    private Date muchStoreDate;
    private Date sellTodayDate;
    private int sellToday;
    private int muchStore;

    public ProductInfo(String name, boolean isStar, Date muchStoreDate, Date sellTodayDate, int sellToday, int muchStore) {
        this.name = name;
        this.isStar = isStar;
        this.muchStoreDate = muchStoreDate;
        this.sellTodayDate = sellTodayDate;
        this.sellToday = sellToday;
        this.muchStore = muchStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStar() {
        return isStar;
    }

    public void setStar(boolean star) {
        isStar = star;
    }

    public Date getMuchStoreDate() {
        return muchStoreDate;
    }

    public void setMuchStoreDate(Date muchStoreDate) {
        this.muchStoreDate = muchStoreDate;
    }

    public Date getSellTodayDate() {
        return sellTodayDate;
    }

    public void setSellTodayDate(Date sellTodayDate) {
        this.sellTodayDate = sellTodayDate;
    }

    public int getSellToday() {
        return sellToday;
    }

    public void setSellToday(int sellToday) {
        this.sellToday = sellToday;
    }

    public int getMuchStore() {
        return muchStore;
    }

    public void setMuchStore(int muchStore) {
        this.muchStore = muchStore;
    }
}
