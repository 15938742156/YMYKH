package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class RedpacketResponse {


    /**
     * code : 1
     * msg : 红包log
     * data : [{"id":1,"uid":10112,"for_id":10122,"forname":"陈明","money":"1.12","is_cli":0,"add_time":"2019-08-16 18:01:51","pay_time":null},{"id":2,"uid":10112,"for_id":10126,"forname":"刘优优","money":"15.12","is_cli":0,"add_time":"2019-08-16 18:01:51","pay_time":"2019-08-16 11:20:12"}]
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

    public static class DataBean {
        /**
         * id : 1
         * uid : 10112
         * for_id : 10122
         * forname : 陈明
         * money : 1.12
         * is_cli : 0
         * add_time : 2019-08-16 18:01:51
         * pay_time : null
         */

        private int id;
        private int uid;
        private int for_id;
        private String forname;
        private String money;
        private int is_cli;
        private String add_time;
        private String pay_time;

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

        public int getFor_id() {
            return for_id;
        }

        public void setFor_id(int for_id) {
            this.for_id = for_id;
        }

        public String getForname() {
            return forname;
        }

        public void setForname(String forname) {
            this.forname = forname;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getIs_cli() {
            return is_cli;
        }

        public void setIs_cli(int is_cli) {
            this.is_cli = is_cli;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }
    }
}
