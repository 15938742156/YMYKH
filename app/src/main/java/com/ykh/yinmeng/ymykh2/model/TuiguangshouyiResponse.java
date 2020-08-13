package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

/**
 * Created At 2018/12/12 23:28.
 *
 * @author larry
 */
public class TuiguangshouyiResponse {


    /**
     * code : 1
     * msg : 推广收益
     * data : {"user":3,"shop":1,"zSum":0,"zShop":0,"money":"14562.19","list":[{"time":"2019-02","money":"1013.88"}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user : 3
         * shop : 1
         * zSum : 0
         * zShop : 0
         * money : 14562.19
         * list : [{"time":"2019-02","money":"1013.88"}]
         */

        private int user;
        private int shop;
        private int zSum;
        private int zShop;
        public String money;
        public List<ListBean> list;

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }

        public int getShop() {
            return shop;
        }

        public void setShop(int shop) {
            this.shop = shop;
        }

        public int getZSum() {
            return zSum;
        }

        public void setZSum(int zSum) {
            this.zSum = zSum;
        }

        public int getZShop() {
            return zShop;
        }

        public void setZShop(int zShop) {
            this.zShop = zShop;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * time : 2019-02
             * money : 1013.88
             */

            public String time;
            public String money;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
