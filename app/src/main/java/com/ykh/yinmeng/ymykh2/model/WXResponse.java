package com.ykh.yinmeng.ymykh2.model;

import com.google.gson.annotations.SerializedName;

public class WXResponse {

    /**
     * code : 1
     * msg : ok
     * data : {"appid":"wxc217d5d3083c1834","partnerid":"1508851941","prepayid":"wx26162133974555abca641f0d2572584704","noncestr":"5c99e10e047fa","timestamp":1553588494,"package":"Sign=WXPay","sign":"48AA5D7D03065DD893F2F1FE1F08A00B"}
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
         * appid : wxc217d5d3083c1834
         * partnerid : 1508851941
         * prepayid : wx26162133974555abca641f0d2572584704
         * noncestr : 5c99e10e047fa
         * timestamp : 1553588494
         * package : Sign=WXPay
         * sign : 48AA5D7D03065DD893F2F1FE1F08A00B
         */

        private String appid;
        private String partnerid;
        private String prepayid;
        private String noncestr;
        private int timestamp;
        @SerializedName("package")
        private String packageX;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
