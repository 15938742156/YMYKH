package com.ykh.yinmeng.ymykh2.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created At 2018/12/7 1:16.
 *
 * @author Angel
 */
public class AddressBean implements Parcelable {

    /**
     * id : 8
     * uid : 10020
     * shrName : 刘莉妨
     * shrMobile : 15938742156
     * shrProvince : 河南省
     * shrCity : 郑州市
     * shrArea : 高新区
     * shrAddress : 八巷321号
     * isDefault : 1
     * createTime : 2018-11-30 01:32:40
     */

    private int id;
    private int uid;
    public String shrName;
    public String shrMobile;
    public String shrProvince;
    public String shrCity;
    public String shrArea;
    public String shrAddress;
    public int isDefault;
    private String createTime;

    protected AddressBean(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        shrName = in.readString();
        shrMobile = in.readString();
        shrProvince = in.readString();
        shrCity = in.readString();
        shrArea = in.readString();
        shrAddress = in.readString();
        isDefault = in.readInt();
        createTime = in.readString();
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel in) {
            return new AddressBean(in);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getShrName() {
        return shrName;
    }

    public void setShrName(String shrName) {
        this.shrName = shrName;
    }

    public String getShrMobile() {
        return shrMobile;
    }

    public void setShrMobile(String shrMobile) {
        this.shrMobile = shrMobile;
    }

    public String getShrProvince() {
        return shrProvince;
    }

    public void setShrProvince(String shrProvince) {
        this.shrProvince = shrProvince;
    }

    public String getShrCity() {
        return shrCity;
    }

    public void setShrCity(String shrCity) {
        this.shrCity = shrCity;
    }

    public String getShrArea() {
        return shrArea;
    }

    public void setShrArea(String shrArea) {
        this.shrArea = shrArea;
    }

    public String getShrAddress() {
        return shrAddress;
    }

    public void setShrAddress(String shrAddress) {
        this.shrAddress = shrAddress;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(uid);
        parcel.writeString(shrName);
        parcel.writeString(shrMobile);
        parcel.writeString(shrProvince);
        parcel.writeString(shrCity);
        parcel.writeString(shrArea);
        parcel.writeString(shrAddress);
        parcel.writeInt(isDefault);
        parcel.writeString(createTime);
    }
}
