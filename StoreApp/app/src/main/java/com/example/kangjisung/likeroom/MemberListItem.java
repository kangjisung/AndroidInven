package com.example.kangjisung.likeroom;

import java.util.Date;

public class MemberListItem {
    private int num;
    private String name;
    private String phone;
    private String point;
    private Date birth;
    private String email;
    private int delete;
    private boolean check;

    public MemberListItem() {}
    public MemberListItem(int num, String name, String phone, String point, Date birth, String email, int delete){
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.point = point;
        this.birth = birth;
        this.email = email;
        this.delete = delete;
    }
    public MemberListItem(int num, String name, String phone, String point, Date birth, String email, int delete, boolean check){
        this.num = num;
        this.name = name;
        this.phone = phone;
        this.point = point;
        this.birth = birth;
        this.email = email;
        this.delete = delete;
        this.check = check;
    }

    public void setNum(int num) {this.num = num;}
    public void setName(String name) {this.name = name;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setPoint(String point) {this.point = point;}
    public void setBirth(Date birth) {this.birth = birth;}
    public void setEmail(String email) {this.email = email;}
    public void setDelete(int delete) {this.delete = delete;}
    public void setCheck(boolean check) {this.check = check;}

    public int getNum() {return this.num;}
    public String getName() {return this.name;}
    public String getPhone() {return this.phone;}
    public String getPoint() {return this.point;}
    public Date getBirth() {return this.birth;}
    public String getEmail() {return this.email;}
    public int getDelete() {return this.delete;}
    public boolean getCheck() {return this.check;}
}