package com.teamdk.android.bakery.objectmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductListItem {
    private int num;
    private String name;
    private int type;
    private int cost;
    private int price;
    private int residual;
    private Date addedDate;
    private int sellToday;
    private int muchStore;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);

    public ProductListItem() {}

    public ProductListItem(int num, String name, int type, int cost, int price, int residual, Date addedDate, Date muchStoreDate, Date sellTodayDate, int sellToday, int muchStore) {
        this.num = num;
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.price = price;
        this.residual = residual;
        this.addedDate = addedDate;
        this.sellToday = sellToday;
        this.muchStore = muchStore;
    }

    public int getNum() {return num;}
    public String getName() {
        return name;
    }
    public int getType() {return type;}
    public int getCost() {return cost;}
    public int getPrice() {return price;}
    public int getResidual() {return residual;}
    public Date getAddedDate() {return addedDate;}
    public int getSellToday() {
        return sellToday;
    }
    public int getMuchStore() {
        return muchStore;
    }
    public String getAddedDateToString() {return dateFormat.format(addedDate);}

    public void setNum(int num) {this.num = num;}
    public void setName(String name) {
        this.name = name;
    }
    public void setType(int type) {this.type = type;}
    public void setCost(int cost) {this.cost = cost;}
    public void setPrice(int price) {this.price = price;}
    public void setResidual(int residual) {this.residual = residual;}
    public void setAddedDate(Date addedDate) {this.addedDate = addedDate;}
    public void setSellToday(int sellToday) {
        this.sellToday = sellToday;
    }
    public void setMuchStore(int muchStore) {
        this.muchStore = muchStore;
    }

    public void setAddedDateToString(String addedDate) {
        try {
            this.addedDate = dateFormat.parse(addedDate);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}