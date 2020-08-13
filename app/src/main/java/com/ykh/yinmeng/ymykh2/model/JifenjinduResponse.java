package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class JifenjinduResponse {

    /**
     * code : 1
     * msg : 审核进度
     * data : {"total":2,"per_page":15,"current_page":1,"last_page":1,"data":[{"id":1019,"gid":3,"sn":"dxcvb","converPic":"http:\\/\\/yinmengpos.oss-cn-zhangjiakou.aliyuncs.com\\/goods\\/2019-04-04\\/5ca5cb1a8a8e3.png","mark":null,"uid":10112,"price":"80.00","address":null,"type":2,"cTime":"2019-05-15 16:31:01","status":0,"cause":null,"checkTime":null,"goodsName":"200元唯品会券","num":200000}]}
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
         * total : 2
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":1019,"gid":3,"sn":"dxcvb","converPic":"http:\\/\\/yinmengpos.oss-cn-zhangjiakou.aliyuncs.com\\/goods\\/2019-04-04\\/5ca5cb1a8a8e3.png","mark":null,"uid":10112,"price":"80.00","address":null,"type":2,"cTime":"2019-05-15 16:31:01","status":0,"cause":null,"checkTime":null,"goodsName":"200元唯品会券","num":200000}]
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
             * id : 1019
             * gid : 3
             * sn : dxcvb
             * converPic : http:\/\/yinmengpos.oss-cn-zhangjiakou.aliyuncs.com\/goods\/2019-04-04\/5ca5cb1a8a8e3.png
             * mark : null
             * uid : 10112
             * price : 80.00
             * address : null
             * type : 2
             * cTime : 2019-05-15 16:31:01
             * status : 0
             * cause : null
             * checkTime : null
             * goodsName : 200元唯品会券
             * num : 200000
             * title:
             */

            private int id;
            private int gid;
            private String sn;
            private String converPic;
            private String mark;
            private int uid;
            private String price;
            private String address;
            private int type;
            private String cTime;
            private int status;
            private String cause;
            private String checkTime;
            private String goodsName;
            private int num;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGid() {
                return gid;
            }

            public void setGid(int gid) {
                this.gid = gid;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public String getConverPic() {
                return converPic;
            }

            public void setConverPic(String converPic) {
                this.converPic = converPic;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCTime() {
                return cTime;
            }

            public void setCTime(String cTime) {
                this.cTime = cTime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCause() {
                return cause;
            }

            public void setCause(String cause) {
                this.cause = cause;
            }

            public String getCheckTime() {
                return checkTime;
            }

            public void setCheckTime(String checkTime) {
                this.checkTime = checkTime;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
