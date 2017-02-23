package com.example.kangjisung.likeroom.FragmentPoint.ListView;

public class PointMainListItem {
    private String name;
    private String phone;
    private String point;
    private boolean check;

    public void setName(String _name) {name = _name;}
    public void setPhone(String _phone) {phone = _phone;}
    public void setPoint(String _point) {point = _point;}
    public void setCheck(boolean _check) {check = _check;}

    public String getName() {return this.name;}
    public String getPhone() {return this.phone;}
    public String getPoint() {return this.point;}
    public boolean getCheck() {return this.check;}
}