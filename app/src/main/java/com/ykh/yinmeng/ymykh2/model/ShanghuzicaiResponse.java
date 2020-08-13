package com.ykh.yinmeng.ymykh2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShanghuzicaiResponse {

    /**
     * code : 1
     * msg : ok
     * data : {"total":7,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":43,"title":"瑞和宝","img_feng":"/Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg","keywords":"瑞和宝","price":0,"description":"瑞和宝","tab":"万5","gz_num":6979,"paizhao":"瑞和宝","fei":"0.55","yun":15,"sum_jl":0,"qixian":"0","is_jia":0},{"id":61,"title":"金正宝0.55","img_feng":"/Public/uploads/goods/2018-09-17/5b9f4521509f3.jpg","keywords":"0","price":300,"description":"0","tab":"0.52结算","gz_num":2569,"paizhao":"金正宝0.55","fei":"0.55","yun":15,"sum_jl":50,"qixian":"30","is_jia":1},{"id":66,"title":"刷宝","img_feng":"/Public/uploads/goods/2018-09-29/5baf49de5831d.jpg","keywords":"秒到","price":100,"description":"秒到","tab":"押金","gz_num":1080,"paizhao":"刷宝","fei":"0.55","yun":15,"sum_jl":70,"qixian":"30","is_jia":1},{"id":46,"title":"点佰趣POS机（自选商户）❤️无货，请勿下单","img_feng":"/Public/uploads/goods/2018-05-11/5af5199d82bd8.jpg","keywords":"自选商户","price":288,"description":"0.61秒到","tab":"押金，推广奖利40元","gz_num":3254,"paizhao":"点佰趣POS机","fei":"0.61","yun":15,"sum_jl":40,"qixian":"10","is_jia":0},{"id":33,"title":"瑞银信G2（自选商户）❤（无货，请勿下单）","img_feng":"/Public/uploads/goods/2018-01-26/5a6aa2228205b.jpg","keywords":"手机APP自选商户","price":283,"description":"手机APP自选商户，每天秒到20万","tab":"押金，推广奖励20元","gz_num":7880,"paizhao":"瑞银信G2（自选商户）","fei":"0.55","yun":15,"sum_jl":20,"qixian":"30","is_jia":0},{"id":32,"title":"拉卡拉收款宝","img_feng":"/Public/uploads/goods/2018-01-26/5a6a993ec32be.jpg","keywords":"押金","price":0,"description":"拉卡拉支付股份有限公司","tab":"推广返利20元","gz_num":6361,"paizhao":"拉卡拉收款宝","fei":"0.68","yun":15,"sum_jl":20,"qixian":"10","is_jia":0},{"id":49,"title":"老瑞和宝批量购买（请勿下单）","img_feng":"/Public/uploads/goods/2018-08-29/5b86000f9d50b.jpg","keywords":"瑞和宝0.6%批量购买","price":30,"description":"瑞和宝0.6%批量购买","tab":"免费","gz_num":2617,"paizhao":"瑞和宝批量","fei":"0.60","yun":15,"sum_jl":0,"qixian":"0","is_jia":1}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * total : 7
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":43,"title":"瑞和宝","img_feng":"/Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg","keywords":"瑞和宝","price":0,"description":"瑞和宝","tab":"万5","gz_num":6979,"paizhao":"瑞和宝","fei":"0.55","yun":15,"sum_jl":0,"qixian":"0","is_jia":0},{"id":61,"title":"金正宝0.55","img_feng":"/Public/uploads/goods/2018-09-17/5b9f4521509f3.jpg","keywords":"0","price":300,"description":"0","tab":"0.52结算","gz_num":2569,"paizhao":"金正宝0.55","fei":"0.55","yun":15,"sum_jl":50,"qixian":"30","is_jia":1},{"id":66,"title":"刷宝","img_feng":"/Public/uploads/goods/2018-09-29/5baf49de5831d.jpg","keywords":"秒到","price":100,"description":"秒到","tab":"押金","gz_num":1080,"paizhao":"刷宝","fei":"0.55","yun":15,"sum_jl":70,"qixian":"30","is_jia":1},{"id":46,"title":"点佰趣POS机（自选商户）❤️无货，请勿下单","img_feng":"/Public/uploads/goods/2018-05-11/5af5199d82bd8.jpg","keywords":"自选商户","price":288,"description":"0.61秒到","tab":"押金，推广奖利40元","gz_num":3254,"paizhao":"点佰趣POS机","fei":"0.61","yun":15,"sum_jl":40,"qixian":"10","is_jia":0},{"id":33,"title":"瑞银信G2（自选商户）❤（无货，请勿下单）","img_feng":"/Public/uploads/goods/2018-01-26/5a6aa2228205b.jpg","keywords":"手机APP自选商户","price":283,"description":"手机APP自选商户，每天秒到20万","tab":"押金，推广奖励20元","gz_num":7880,"paizhao":"瑞银信G2（自选商户）","fei":"0.55","yun":15,"sum_jl":20,"qixian":"30","is_jia":0},{"id":32,"title":"拉卡拉收款宝","img_feng":"/Public/uploads/goods/2018-01-26/5a6a993ec32be.jpg","keywords":"押金","price":0,"description":"拉卡拉支付股份有限公司","tab":"推广返利20元","gz_num":6361,"paizhao":"拉卡拉收款宝","fei":"0.68","yun":15,"sum_jl":20,"qixian":"10","is_jia":0},{"id":49,"title":"老瑞和宝批量购买（请勿下单）","img_feng":"/Public/uploads/goods/2018-08-29/5b86000f9d50b.jpg","keywords":"瑞和宝0.6%批量购买","price":30,"description":"瑞和宝0.6%批量购买","tab":"免费","gz_num":2617,"paizhao":"瑞和宝批量","fei":"0.60","yun":15,"sum_jl":0,"qixian":"0","is_jia":1}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Parcelable {
            /**
             * id : 43
             * title : 瑞和宝
             * img_feng : /Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg
             * keywords : 瑞和宝
             * price : 0
             * description : 瑞和宝
             * tab : 万5
             * gz_num : 6979
             * paizhao : 瑞和宝
             * fei : 0.55
             * yun : 15
             * sum_jl : 0
             * qixian : 0
             * is_jia : 0
             */

            public int id;
            public String title;
            public String img_feng;
            public String keywords;
            public double price;
            public String description;
            public String tab;
            public int gz_num;
            public String paizhao;
            public String fei;
            public double yun;
            public int sum_jl;
            public String qixian;
            public int is_jia;
            protected DataBean(Parcel in){
                id = in.readInt();
                title = in.readString();
                img_feng = in.readString();
                keywords = in.readString();
                price = in.readDouble();
                description = in.readString();
                tab = in.readString();
                gz_num = in.readInt();
                paizhao = in.readString();
                fei = in.readString();
                yun = in.readDouble();
                sum_jl = in.readInt();
                qixian = in.readString();
                is_jia = in.readInt();

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

            public String getImg_feng() {
                return img_feng;
            }

            public void setImg_feng(String img_feng) {
                this.img_feng = img_feng;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
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

            public String getPaizhao() {
                return paizhao;
            }

            public void setPaizhao(String paizhao) {
                this.paizhao = paizhao;
            }

            public String getFei() {
                return fei;
            }

            public void setFei(String fei) {
                this.fei = fei;
            }

            public double getYun() {
                return yun;
            }

            public void setYun(double yun) {
                this.yun = yun;
            }

            public int getSum_jl() {
                return sum_jl;
            }

            public void setSum_jl(int sum_jl) {
                this.sum_jl = sum_jl;
            }

            public String getQixian() {
                return qixian;
            }

            public void setQixian(String qixian) {
                this.qixian = qixian;
            }

            public int getIs_jia() {
                return is_jia;
            }

            public void setIs_jia(int is_jia) {
                this.is_jia = is_jia;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(id);
                parcel.writeString(title);
                parcel.writeString(img_feng);
                parcel.writeString(keywords);
                parcel.writeDouble(price);
                parcel.writeString(description);
                parcel.writeString(tab);
                parcel.writeInt(gz_num);
                parcel.writeString(paizhao);
                parcel.writeString(fei);
                parcel.writeDouble(yun);
                parcel.writeInt(sum_jl);
                parcel.writeString(qixian);
                parcel.writeInt(is_jia);

            }
        }
    }
}
