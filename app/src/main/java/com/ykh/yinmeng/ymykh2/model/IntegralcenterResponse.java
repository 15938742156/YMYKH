package com.ykh.yinmeng.ymykh2.model;

public class IntegralcenterResponse {

    /**
     * code : 1
     * msg : 积分中心
     * data : {"id":10112,"truename":"王国五","tel":"15537189797","level":0,"uid":10112,"coin":"0.00","inCoin":"0.00","outCoin":"0.00","inRank":"0.00","status":1}
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
         * id : 10112
         * truename : 王国五
         * tel : 15537189797
         * level : 0
         * uid : 10112
         * coin : 0.00
         * inCoin : 0.00
         * outCoin : 0.00
         * inRank : 0.00
         * status : 1
         */

        private int id;
        private String truename;
        private String tel;
        private int level;
        private int uid;
        private String coin;
        private String inCoin;
        private String outCoin;
        private String inRank;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTruename() {
            return truename;
        }

        public void setTruename(String truename) {
            this.truename = truename;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getInCoin() {
            return inCoin;
        }

        public void setInCoin(String inCoin) {
            this.inCoin = inCoin;
        }

        public String getOutCoin() {
            return outCoin;
        }

        public void setOutCoin(String outCoin) {
            this.outCoin = outCoin;
        }

        public String getInRank() {
            return inRank;
        }

        public void setInRank(String inRank) {
            this.inRank = inRank;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
