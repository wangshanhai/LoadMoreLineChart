package com.example.loadmorelinechart.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DevicePowerItem implements Parcelable {

    /**
     * pid : BP.20201230.1344213866124546048.Pi
     * sn : null
     * time : 1615824000000
     * dqf : 1
     * type : null
     * val : 731.0
     * unit : null
     * remark : null
     * deviceCode : null
     * appDeviceCode : null
     * gmtCreate : null
     * deviceType : null
     */

    private String pid;
    private long time;
    private int dqf;
    private String val;
    private String unit;
    private String name;
    private String value;

    private int index;//记录曲线数据的index

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getDqf() {
        return dqf;
    }

    public void setDqf(int dqf) {
        this.dqf = dqf;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pid);
        dest.writeLong(this.time);
        dest.writeInt(this.dqf);
        dest.writeString(this.val);
        dest.writeString(this.unit);
        dest.writeString(this.name);
        dest.writeString(this.value);
        dest.writeInt(this.index);
    }

    public void readFromParcel(Parcel source) {
        this.pid = source.readString();
        this.time = source.readLong();
        this.dqf = source.readInt();
        this.val = source.readString();
        this.unit = source.readString();
        this.name = source.readString();
        this.value = source.readString();
        this.index = source.readInt();
    }

    public DevicePowerItem() {
    }

    protected DevicePowerItem(Parcel in) {
        this.pid = in.readString();
        this.time = in.readLong();
        this.dqf = in.readInt();
        this.val = in.readString();
        this.unit = in.readString();
        this.name = in.readString();
        this.value = in.readString();
        this.index = in.readInt();
    }

    public static final Creator<DevicePowerItem> CREATOR = new Creator<DevicePowerItem>() {
        @Override
        public DevicePowerItem createFromParcel(Parcel source) {
            return new DevicePowerItem(source);
        }

        @Override
        public DevicePowerItem[] newArray(int size) {
            return new DevicePowerItem[size];
        }
    };
}
