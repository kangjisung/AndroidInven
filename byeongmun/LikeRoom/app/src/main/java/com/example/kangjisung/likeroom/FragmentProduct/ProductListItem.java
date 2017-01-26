package com.example.kangjisung.likeroom.FragmentProduct;

import java.util.Date;

public class ProductListItem {
    private String name;
    private boolean isStar;
    private Date registeredDate;
    private Date muchStoreDate;
    private Date sellTodayDate;
    private int sellToday;
    private int muchStore;

    public ProductListItem(String name, boolean isStar, Date registeredDate, Date muchStoreDate, Date sellTodayDate, int sellToday, int muchStore) {
        this.name = name;
        this.isStar = isStar;
        this.registeredDate = registeredDate;
        this.muchStoreDate = muchStoreDate;
        this.sellTodayDate = sellTodayDate;
        this.sellToday = sellToday;
        this.muchStore = muchStore;
    }

    public String getName() {
        return name;
    }
    public boolean isStar() {
        return isStar;
    }
    public Date getRegisteredDate() {
        return registeredDate;
    }
    public int getMuchStore() {
        return muchStore;
    }
    public Date getMuchStoreDate() {
        return muchStoreDate;
    }
    public int getSellToday() {
        return sellToday;
    }
    public Date getSellTodayDate() {
        return sellTodayDate;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setStar(boolean star) {
        isStar = star;
    }
    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }
    public void setMuchStore(int muchStore) {
        this.muchStore = muchStore;
    }
    public void setMuchStoreDate(Date muchStoreDate) {
        this.muchStoreDate = muchStoreDate;
    }
    public void setSellToday(int sellToday) {
        this.sellToday = sellToday;
    }
    public void setSellTodayDate(Date sellTodayDate) {
        this.sellTodayDate = sellTodayDate;
    }
}