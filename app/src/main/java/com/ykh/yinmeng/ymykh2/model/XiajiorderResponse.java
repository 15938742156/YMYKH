package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class XiajiorderResponse {

    /**
     * code : 1
     * msg : 下级订单
     * data : {"total":1,"per_page":10,"current_page":1,"last_page":1,"data":[{"id":116,"out_trade_no":"YM1552378075532501000","order_money":"15.00","pay_money":"15.00","exp_fee":"15.00","num":1,"exp":null,"exp_num":null,"status":0,"createtime":"2019-03-12 16:07:55","title":"瑞和宝","img_feng":"/Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg","tab":"万5","description":"瑞和宝","name":"刘莉妨","tel":"15938742156","price":0}]}
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
         * total : 1
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"id":116,"out_trade_no":"YM1552378075532501000","order_money":"15.00","pay_money":"15.00","exp_fee":"15.00","num":1,"exp":null,"exp_num":null,"status":0,"createtime":"2019-03-12 16:07:55","title":"瑞和宝","img_feng":"/Public/uploads/goods/2018-04-28/5ae42b6445c72.jpg","tab":"万5","description":"瑞和宝","name":"刘莉妨","tel":"15938742156","price":0}]
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

        public static class DataBean {
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
             * name : 刘莉妨
             * tel : 15938742156
             * price : 0
             */

            private int id;
            private String out_trade_no;
            private String order_money;
            private String pay_money;
            private String exp_fee;
            private int num;
            private Object exp;
            private Object exp_num;
            private int status;
            private String createtime;
            private String title;
            private String img_feng;
            private String tab;
            private String description;
            private String name;
            private String tel;
            private int price;

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

            public Object getExp() {
                return exp;
            }

            public void setExp(Object exp) {
                this.exp = exp;
            }

            public Object getExp_num() {
                return exp_num;
            }

            public void setExp_num(Object exp_num) {
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }
        }
    }
}
