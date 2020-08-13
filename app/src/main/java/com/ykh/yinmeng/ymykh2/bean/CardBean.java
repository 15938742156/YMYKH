package com.ykh.yinmeng.ymykh2.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 银行卡列表
 */
public class CardBean implements Parcelable{


    /**
     * id : 21
     * uid : 34109
     * banks : 中国银行
     * branch : 河南省-郑州市-金水区-农业路支行
     * banksNumber : 45596558633333
     * banksAccount : 李四
     * status : 0
     */

    public int id;
    public int uid;
    public String banks;
    public String branch;
    public String banksNumber;
    public String banksAccount;
    public int status;

    protected CardBean(Parcel in) {
        id = in.readInt();
        uid = in.readInt();
        banks = in.readString();
        branch = in.readString();
        banksNumber = in.readString();
        banksAccount = in.readString();
        status = in.readInt();
    }

    public static final Creator<CardBean> CREATOR = new Creator<CardBean>() {
        @Override
        public CardBean createFromParcel(Parcel in) {
            return new CardBean(in);
        }

        @Override
        public CardBean[] newArray(int size) {
            return new CardBean[size];
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

    public String getBanks() {
        return banks;
    }

    public void setBanks(String banks) {
        this.banks = banks;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBanksNumber() {
        return banksNumber;
    }

    public void setBanksNumber(String banksNumber) {
        this.banksNumber = banksNumber;
    }

    public String getBanksAccount() {
        return banksAccount;
    }

    public void setBanksAccount(String banksAccount) {
        this.banksAccount = banksAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(uid);
        parcel.writeString(banks);
        parcel.writeString(branch);
        parcel.writeString(banksNumber);
        parcel.writeString(banksAccount);
        parcel.writeInt(status);
    }
}
