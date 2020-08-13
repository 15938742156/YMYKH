package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class TuanduipaimingResponse {

    /**
     * code : 1
     * msg :
     * data : [{"uid":10030,"inRank":"11900.00","name":"张楠","tel":"15836789046"}]
     */

    private int code;
    private String msg;
    private List<PaimingBean> data;

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

    public List<PaimingBean> getData() {
        return data;
    }

    public void setData(List<PaimingBean> data) {
        this.data = data;
    }

    public static class PaimingBean {
        /**
         * uid : 10030
         * inRank : 11900.00
         * name : 张楠
         * tel : 15836789046
         */

        private int uid;
        public String inRank;
        public String name;
        public String tel;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getInRank() {
            return inRank;
        }

        public void setInRank(String inRank) {
            this.inRank = inRank;
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
    }
}
