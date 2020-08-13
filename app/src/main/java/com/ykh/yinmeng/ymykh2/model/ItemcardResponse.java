package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class ItemcardResponse {
            // 1自主，2实物，3客服
    /**
     * code : 1
     * msg : 银行分类列表
     * data : [{"id":1,"cateName":"50元肯德基优惠卷","bankId":1,"unit":"100000","type":2,"integral":"5.2","detail":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-04-18/5cb83c7983af1.jpg"}]
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
         * cateName : 50元肯德基优惠卷
         * bankId : 1
         * unit : 100000
         * type : 2
         * integral : 5.2
         * detail : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-04-18/5cb83c7983af1.jpg
         */

        private int id;
        private String cateName;
        private int bankId;
        private String unit;
        private int type;
        private String integral;
        private String detail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCateName() {
            return cateName;
        }

        public void setCateName(String cateName) {
            this.cateName = cateName;
        }

        public int getBankId() {
            return bankId;
        }

        public void setBankId(int bankId) {
            this.bankId = bankId;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}
