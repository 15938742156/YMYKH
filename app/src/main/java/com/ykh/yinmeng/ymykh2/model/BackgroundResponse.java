package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class BackgroundResponse {


    /**
     * code : 1
     * msg : 背景图
     * data : [{"id":13,"logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-08-23/5d5f886f9cf69.jpg"},{"id":12,"logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-08-23/5d5f884e92db1.jpg"},{"id":11,"logo":"http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-08-23/5d5f88384e544.jpg"}]
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
         * id : 13
         * logo : http://yinmengpos.oss-cn-zhangjiakou.aliyuncs.com/goods/2019-08-23/5d5f886f9cf69.jpg
         */

        private int id;
        private String logo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
