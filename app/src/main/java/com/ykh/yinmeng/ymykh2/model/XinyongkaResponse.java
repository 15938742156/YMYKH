package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class XinyongkaResponse {


    /**
     * code : 1
     * msg : ok
     * data : [{"id":10,"cardName":"中信银行信用卡","logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-10-24/5bcfdfd77a00c.jpg","desc":"推广奖励75元","bankId":41,"reward_sum":"75.00","is_reward":1,"gz_num":2038,"status":1}]
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
         * id : 10
         * cardName : 中信银行信用卡
         * logo : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-10-24/5bcfdfd77a00c.jpg
         * desc : 推广奖励75元
         * bankId : 41
         * reward_sum : 75.00
         * is_reward : 1
         * gz_num : 2038
         * status : 1
         */

        private int id;
        private String cardName;
        private String logo;
        private String desc;
        private int bankId;
        private String reward_sum;
        private int is_reward;
        private int gz_num;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public String getReward_sum() {
            return reward_sum;
        }

        public void setReward_sum(String reward_sum) {
            this.reward_sum = reward_sum;
        }

        public int getIs_reward() {
            return is_reward;
        }

        public void setIs_reward(int is_reward) {
            this.is_reward = is_reward;
        }

        public int getGz_num() {
            return gz_num;
        }

        public void setGz_num(int gz_num) {
            this.gz_num = gz_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
