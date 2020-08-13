package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class RebateResponse {

    /**
     * code : 1
     * msg : 返利列表
     * data : [{"bian":null,"money":"0.01","addtime":1552445674,"type":4,"gid":36721,"cause":"推广红包"},{"bian":"829501000010818","money":"5.00","addtime":1551154261,"type":5,"gid":71,"paizhao":"联动优势0.55","likebian":"829*********818"}]
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
         * bian : null
         * money : 0.01
         * addtime : 1552445674
         * type : 4
         * gid : 36721
         * cause : 推广红包
         * paizhao : 联动优势0.55
         * likebian : 829*********818
         * change_num: 30
         * add_time : 1552445674
         */

        public String bian;
        public String money;
        public int addtime;
        public int type;
        public int gid;
        public String cause;
        public String paizhao;
        public String likebian;
        public int change_num;
        public int add_time;

        public String getBian() {
            return bian;
        }

        public void setBian(String bian) {
            this.bian = bian;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int addtime) {
            this.add_time = add_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }

        public String getPaizhao() {
            return paizhao;
        }

        public void setPaizhao(String paizhao) {
            this.paizhao = paizhao;
        }

        public String getLikebian() {
            return likebian;
        }

        public void setLikebian(String likebian) {
            this.likebian = likebian;
        }

        public int getChange_num() {
            return change_num;
        }

        public void setChange_num(int change_num) {
            this.change_num = change_num;
        }
    }
}
