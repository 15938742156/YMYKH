package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class OrderdikoujiluResponse {

    /**
     * code : 1
     * msg : ok
     * data : [{"change_num":15,"symbol":-1,"cause":"线上订单抵现","add_time":1553742166,"stu":1}]
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
         * change_num : 15
         * symbol : -1
         * cause : 线上订单抵现
         * add_time : 1553742166
         * stu : 1
         */

        private double change_num;
        private int symbol;
        private String cause;
        private int add_time;
        private int stu;

        public double getChange_num() {
            return change_num;
        }

        public void setChange_num(double change_num) {
            this.change_num = change_num;
        }

        public int getSymbol() {
            return symbol;
        }

        public void setSymbol(int symbol) {
            this.symbol = symbol;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
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
    }
}
