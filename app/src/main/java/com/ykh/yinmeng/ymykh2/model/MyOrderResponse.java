package com.ykh.yinmeng.ymykh2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrderResponse {
    /**
     * code : 1
     * msg : pos机订单
     * data : {"total":10,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":75,"out_trade_no":"YM1544686837106681000","order_money":"120.00","pay_money":"120.00","pay_type":0,"num":1,"rece_shr":"河南省郑州市中原区 东大街喜大家","exp":null,"exp_num":null,"pay_status":0,"createtime":"2018-12-13 15:40:37","goods_name":"至尊礼包","logo":"http://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-12-04/5c061c45bb6f3.png","tab":"MPOS","description":"","price":"120.00"}]}
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
         * total : 10
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":75,"out_trade_no":"YM1544686837106681000","order_money":"120.00","pay_money":"120.00","pay_type":0,"num":1,"rece_shr":"河南省郑州市中原区 东大街喜大家","exp":null,"exp_num":null,"pay_status":0,"createtime":"2018-12-13 15:40:37","goods_name":"至尊礼包","logo":"http://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-12-04/5c061c45bb6f3.png","tab":"MPOS","description":"","price":"120.00"}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        @SerializedName("data")
        private List<OrderBean> dataX;

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

        public List<OrderBean> getDataX() {
            return dataX;
        }

        public void setDataX(List<OrderBean> dataX) {
            this.dataX = dataX;
        }

        public static class OrderBean implements Parcelable{
            /**
             * id : 116
             * out_trade_no : YM1552378075532501000
             * order_money : 15.00
             * pay_money : 15.00
             * exp_fee : 15.00
             * num : 1
             * exp : null
             * exp_num : null
             * status : 0
             * createtime : 2019-03-12 16:07:55
             * title : 瑞和宝
             * img_feng : /Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg
             * tab : 万5
             * description : 瑞和宝
             * price : 0
             * rece_name : 刘莉妨
             * rece_tel : 15938742156
             * rece_shr : 河南省郑州市金水区 财富广场
             */

            public int id;
            public String out_trade_no;
            public String order_money;
            public String pay_money;
            public String exp_fee;
            public int num;
            public String exp;
            public String exp_num;
            public int status;
            public String createtime;
            public String title;
            public String img_feng;
            public String tab;
            public String description;
            public double price;
            public String rece_name;
            public String rece_tel;
            public String rece_shr;

            protected OrderBean(Parcel in) {
                id = in.readInt();
                out_trade_no = in.readString();
                order_money = in.readString();
                pay_money = in.readString();
                exp_fee = in.readString();
                num = in.readInt();
                exp = in.readString();
                exp_num = in.readString();
                status = in.readInt();
                createtime = in.readString();
                title = in.readString();
                img_feng = in.readString();
                tab = in.readString();
                description = in.readString();
                price = in.readDouble();
                rece_name = in.readString();
                rece_tel = in.readString();
                rece_shr = in.readString();
            }

            public static final Creator<OrderBean> CREATOR = new Creator<OrderBean>() {
                @Override
                public OrderBean createFromParcel(Parcel in) {
                    return new OrderBean(in);
                }

                @Override
                public OrderBean[] newArray(int size) {
                    return new OrderBean[size];
                }
            };

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getOrder_money() {
                return order_money;
            }

            public void setOrder_money(String order_money) {
                this.order_money = order_money;
            }

            public String getPay_money() {
                return pay_money;
            }

            public void setPay_money(String pay_money) {
                this.pay_money = pay_money;
            }

            public String getExp_fee() {
                return exp_fee;
            }

            public void setExp_fee(String exp_fee) {
                this.exp_fee = exp_fee;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getExp() {
                return exp;
            }

            public void setExp(String exp) {
                this.exp = exp;
            }

            public String getExp_num() {
                return exp_num;
            }

            public void setExp_num(String exp_num) {
                this.exp_num = exp_num;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
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

            public String getTab() {
                return tab;
            }

            public void setTab(String tab) {
                this.tab = tab;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
            }

            public String getRece_name() {
                return rece_name;
            }

            public void setRece_name(String rece_name) {
                this.rece_name = rece_name;
            }

            public String getRece_tel() {
                return rece_tel;
            }

            public void setRece_tel(String rece_tel) {
                this.rece_tel = rece_tel;
            }

            public String getRece_shr() {
                return rece_shr;
            }

            public void setRece_shr(String rece_shr) {
                this.rece_shr = rece_shr;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

                parcel.writeInt(id);
                parcel.writeString(out_trade_no);
                parcel.writeString(order_money);
                parcel.writeString(pay_money);
                parcel.writeString(exp_fee);
                parcel.writeInt(num);
                parcel.writeString(exp);
                parcel.writeString(exp_num);
                parcel.writeInt(status);
                parcel.writeString(createtime);
                parcel.writeString(title);
                parcel.writeString(img_feng);
                parcel.writeString(tab);
                parcel.writeString(description);
                parcel.writeDouble(price);
                parcel.writeString(rece_name);
                parcel.writeString(rece_tel);
                parcel.writeString(rece_shr);
            }
        }
    }
}
