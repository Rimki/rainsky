package com.example.k.rainsky;

public class Notice {

    String address;
    String itemname;

    public Notice(String address, String itemname) {
        this.address = address;
        this.itemname = itemname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}
