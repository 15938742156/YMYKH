package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class TixianjiluResponse {


    /**
     * code : 1
     * msg : ok
     * data : [{"id":4386,"bian_num":"201903281045035983","tixian_num":100,"add_time":1553741103,"stu":1,"uid":34109,"sort":0,"is_show":1,"bank":"中国银行","kaid":"12345678987654321","feilv":7,"truename":"文言彪"}]
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
         * id : 4386
         * bian_num : 201903281045035983
         * tixian_num : 100
         * add_time : 1553741103
         * stu : 1
         * uid : 34109
         * sort : 0
         * is_show : 1
         * bank : 中国银行
         * kaid : 12345678987654321
         * feilv : 7
         * truename : 文言彪
         */

        private int id;
        private String bian_num;
        private double tixian_num;
        private int add_time;
        private int stu;
        private int uid;
        private int sort;
        private int is_show;
        private String bank;
        private String kaid;
        private int feilv;
        private String truename;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBian_num() {
            return bian_num;
        }

        public void setBian_num(String bian_num) {
            this.bian_num = bian_num;
        }

        public double getTixian_num() {
            return tixian_num;
        }

        public void setTixian_num(double tixian_num) {
            this.tixian_num = tixian_num;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getStu() {
            return stu;
        }

        public void setStu(int stu) {
            this.stu = stu;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getKaid() {
            return kaid;
        }

        public void setKaid(String kaid) {
            this.kaid = kaid;
        }

        public int getFeilv() {
            return feilv;
        }

        public void setFeilv(int feilv) {
            this.feilv = feilv;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }
    }
}
