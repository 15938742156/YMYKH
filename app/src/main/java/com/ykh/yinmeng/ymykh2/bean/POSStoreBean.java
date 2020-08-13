package com.ykh.yinmeng.ymykh2.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class POSStoreBean implements Parcelable {

    /**
     * id : 6
     * goods_name : 创业礼包
     * logo : http://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-12-28/5c25bca4b0b86.png
     * rate_sch : 0.65
     * price : 120.00
     * description :
     * tab : MPOS
     * gz_num : 2005
     * license : 优动联盟
     * type : 2
     * keywords : 最新产品全新上市
     * num : 5
     */

    private int id;
    public String goods_name;
    public String logo;
    public String rate_sch;
    public String price;
    public String description;
    public String tab;
    public int gz_num;
    public String license;
    public int type;
    public String keywords;
    public int num;
    protected POSStoreBean(Parcel in) {
        id = in.readInt();
        goods_name = in.readString();
        logo = in.readString();
        price = in.readString();
        type = in.readInt();
        num = in.readInt();
    }

    public static final Creator<POSStoreBean> CREATOR = new Creator<POSStoreBean>() {
        @Override
        public POSStoreBean createFromParcel(Parcel in) {
            return new POSStoreBean(in);
        }

        @Override
        public POSStoreBean[] newArray(int size) {
            return new POSStoreBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRate_sch() {
        return rate_sch;
    }

    public void setRate_sch(String rate_sch) {
        this.rate_sch = rate_sch;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public int getGz_num() {
        return gz_num;
    }

    public void setGz_num(int gz_num) {
        this.gz_num = gz_num;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(goods_name);
        parcel.writeString(logo);
        parcel.writeString(price);
        parcel.writeInt(type);
        parcel.writeInt(num);
    }
}
