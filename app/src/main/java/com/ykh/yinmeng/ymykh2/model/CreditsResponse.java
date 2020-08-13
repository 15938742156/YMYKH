package com.ykh.yinmeng.ymykh2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CreditsResponse {


    /**
     * code : 1
     * msg : 银行列表
     * data : [{"id":1,"title":"招商银行","desc":"很贵1","logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-04-17/5cb6f02c24215.png","ratio":"0.256"},{"id":2,"title":"民生银行","desc":"好用","logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-04-18/5cb83a0bede95.png","ratio":"0.154"}]
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
         * title : 招商银行
         * desc : 很贵1
         * logo : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-04-17/5cb6f02c24215.png
         * ratio : 0.256
         */

        public int id;
        public String title;
        public String desc;
        public String logo;
        public String ratio;

        protected DataBean(Parcel in){
            id = in.readInt();
            title = in.readString();
            desc = in.readString();
            logo = in.readString();
            ratio = in.readString();

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeString(title);
            parcel.writeString(desc);
            parcel.writeString(logo);
            parcel.writeString(ratio);

        }
    }
}
