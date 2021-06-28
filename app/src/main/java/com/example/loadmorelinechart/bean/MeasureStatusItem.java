package com.example.loadmorelinechart.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MeasureStatusItem implements Parcelable {

    private String fmeasureId;
    private String fname;
    private int resColorId;
    private boolean isSelect;
    private String fUnit;
    private List<DevicePowerItem> dataList;

    public String getfUnit() {
        return fUnit;
    }

    public void setfUnit(String fUnit) {
        this.fUnit = fUnit;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public List<DevicePowerItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<DevicePowerItem> dataList) {
        this.dataList = dataList;
    }

    public int getResColorId() {
        return resColorId;
    }

    public void setResColorId(int resColorId) {
        this.resColorId = resColorId;
    }


    public String getFmeasureId() {
        return fmeasureId;
    }

    public void setFmeasureId(String fmeasureId) {
        this.fmeasureId = fmeasureId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public MeasureStatusItem() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fmeasureId);
        dest.writeString(this.fname);
        dest.writeInt(this.resColorId);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.fUnit);
        dest.writeTypedList(this.dataList);
    }

    protected MeasureStatusItem(Parcel in) {
        this.fmeasureId = in.readString();
        this.fname = in.readString();
        this.resColorId = in.readInt();
        this.isSelect = in.readByte() != 0;
        this.fUnit = in.readString();
        this.dataList = in.createTypedArrayList(DevicePowerItem.CREATOR);
    }

    public static final Creator<MeasureStatusItem> CREATOR = new Creator<MeasureStatusItem>() {
        @Override
        public MeasureStatusItem createFromParcel(Parcel source) {
            return new MeasureStatusItem(source);
        }

        @Override
        public MeasureStatusItem[] newArray(int size) {
            return new MeasureStatusItem[size];
        }
    };
}
