package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class JihuomingxiResponse {


    /**
     * code : 1
     * msg : 激活明细
     * data : [{"xinghao":"32021106","title":"杉德多多付❤，暂时不接受订单","tel":"13526579091","name":null,"addtime":1522122900,"isxian":3}]
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
         * xinghao : 32021106
         * title : 杉德多多付❤，暂时不接受订单
         * tel : 13526579091
         * name : null
         * addtime : 1522122900
         * isxian : 3
         */

        private String xinghao;
        private String title;
        private String tel;
        private String name;
        private int addtime;
        private int isxian;

        public String getXinghao() {
            return xinghao;
        }

        public void setXinghao(String xinghao) {
            this.xinghao = xinghao;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public int getIsxian() {
            return isxian;
        }

        public void setIsxian(int isxian) {
            this.isxian = isxian;
        }
    }
}
