package com.ykh.yinmeng.ymykh2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class JifenItemgoodsResponse {

    /**
     * code : 1
     * msg : 商品列表
     * data : [{"id":1,"goodsName":"好吃的","categoryId":3,"bankId":2,"num":10000,"price":"2.30","onePrice":"3.20","highPrice":"5.30"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * goodsName : 好吃的
         * categoryId : 3
         * bankId : 2
         * num : 10000
         * price : 2.30
         * onePrice : 3.20
         * highPrice : 5.30
         */

        public int id;
        public String goodsName;
        public int categoryId;
        public int bankId;
        public int num;
        public String price;
        public String onePrice;
        public String highPrice;
        public boolean isSelect = false;

        protected DataBean(Parcel in){
            id = in.readInt();
            goodsName = in.readString();
            categoryId = in.readInt();
            bankId = in.readInt();
            num = in.readInt();
            price = in.readString();
            onePrice = in.readString();
            highPrice = in.readString();

        }
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOnePrice() {
            return onePrice;
        }

        public void setOnePrice(String onePrice) {
            this.onePrice = onePrice;
        }

        public String getHighPrice() {
            return highPrice;
        }

        public void setHighPrice(String highPrice) {
            this.highPrice = highPrice;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeInt(id);
            parcel.writeString(goodsName);
            parcel.writeInt(categoryId);
            parcel.writeInt(bankId);
            parcel.writeInt(num);
            parcel.writeString(price);
            parcel.writeString(onePrice);
            parcel.writeString(highPrice);
        }
    }
}
