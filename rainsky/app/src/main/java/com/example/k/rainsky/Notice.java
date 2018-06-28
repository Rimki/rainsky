package com.example.k.rainsky;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.ParameterizedType;

public class Notice implements Parcelable {

    String address;
    String itemname;

    public Notice(String address, String itemname) {
        this.address = address;
        this.itemname = itemname;
    }
    public Notice(Parcel in) {
        this.address = in.readString();
        this.itemname = in.readString();
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel in) {
            return new Notice(in);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.address);
            parcel.writeString(this.itemname);
    }
}
