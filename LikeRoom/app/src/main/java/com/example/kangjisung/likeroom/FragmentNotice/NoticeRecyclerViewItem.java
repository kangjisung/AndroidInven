package com.example.kangjisung.likeroom.FragmentNotice;

import android.graphics.drawable.Drawable;

import java.util.Calendar;

public class NoticeRecyclerViewItem
{

    //NoticeRecyclerViewAdapter에서 사용하는 메소드나 변수들을 정의해두었다.

    private String title;
    private String body;
    private Calendar startDate;
    private Calendar endDate;
    private int type;

    String storeName, storeAddress, storePhoneNumber;
    Drawable imgOfStore;

    public void setTitle(String _title) {title = _title;}
    public void setBody(String _body) {body = _body;}
    public void setStartDate(Calendar _startDate) {startDate = _startDate;}
    public void setEndDate(Calendar _endDate) {endDate = _endDate;}
    public void setType(int _type) {type = _type;}
    void SetStoreName(String storeName) {
        this.storeName = storeName;
    }
    void SetStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }
    void SetStorePhoneNumber(String storePhoneNumber) {
        this.storePhoneNumber = storePhoneNumber;
    }
    void SetStoreImage(Drawable imgOfStore) {
        this.imgOfStore = imgOfStore;
    }

    public String getTitle() {return this.title;}
    public String getBody() {return this.body;}
    public Calendar getStartDate() {return this.startDate;}
    public Calendar getEndDate() {return this.endDate;}
    public int getType() {return this.type;}
    String GetStoreName() {
        return storeName;
    }
    String GetStoreAddress() {
        return storeAddress;
    }
    String GetStorePhoneNumber() {
        return storePhoneNumber;
    }
    Drawable GetStoreImage() {
        return imgOfStore;
    }
}