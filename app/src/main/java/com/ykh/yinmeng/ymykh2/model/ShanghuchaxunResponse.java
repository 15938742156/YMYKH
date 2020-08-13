package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class ShanghuchaxunResponse {


    /**
     * code : 1
     * msg : 商户信息
     * data : [{"xinghao":"88750D170117257","title":"瑞银信G2（自选商户）❤（无货，请勿下单）","img_feng":"/Public/uploads/goods/2018-01-26/5a6aa2228205b.jpg","tel":"13782833088","name":null,"price":283}]
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
         * xinghao : 88750D170117257
         * title : 瑞银信G2（自选商户）❤（无货，请勿下单）
         * img_feng : /Public/uploads/goods/2018-01-26/5a6aa2228205b.jpg
         * tel : 13782833088
         * name : null
         * price : 283
         */

        private String xinghao;
        private String title;
        private String img_feng;
        private String tel;
        private String name;
        private int price;

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

        public String getImg_feng() {
            return img_feng;
        }

        public void setImg_feng(String img_feng) {
            this.img_feng = img_feng;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
