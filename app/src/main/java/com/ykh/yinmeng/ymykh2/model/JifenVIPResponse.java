package com.ykh.yinmeng.ymykh2.model;

public class JifenVIPResponse {


    /**
     * code : 0
     * msg : 积分vip
     * data : {"gold":"180","jewel":"588","upgrade":"408"}
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
         * gold : 180
         * jewel : 588
         * upgrade : 408
         */

        private String gold;
        private String jewel;
        private String upgrade;

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getJewel() {
            return jewel;
        }

        public void setJewel(String jewel) {
            this.jewel = jewel;
        }

        public String getUpgrade() {
            return upgrade;
        }

        public void setUpgrade(String upgrade) {
            this.upgrade = upgrade;
        }
    }
}
