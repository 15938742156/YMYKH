package com.ykh.yinmeng.ymykh2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewXinyongkaResponse {

    /**
     * code : 200
     * message : success
     * response_time : 2019-06-03 09:39:17
     * data : {"page":{"totalPage":3,"totalCount":"23"},"list":[{"id":"51","name":"浦发银行梦卡","cooperative_bacooperative_bank_id":"141","logo_img":"/20190429/8531711556517863626123.png","annual_fee_status":"1","annual_fee":"终身免年费","probability":"0","abstract":"天天5折享美食，更有日日优惠福利券","characteristic":" <p>1、浦发梦卡终身免年费。<\/p> <p>2、浦发Visa梦卡是浦发银行首张符合EMV标准且具有Visa payWave功能的芯片信用卡，采用\"芯片+磁条\"的形式，不仅具有传统的磁条识别系统,还采用了高度集成的芯片作为信息载体,为信用卡的安全和功能拓展奠定了坚实的基础，为您带来更好的支付体验。<\/p> <p>3、用卡更安全：采用高度集成的芯片为载体，保障您的用卡安全。<\/p> <p>4、支付更便捷：只需在收银台的感应式卡片阅读机前感应卡片，即可轻松又安全的完成付款。<\/p>","raiders":" <p style=\"white-space: normal;\">1、年龄要求：18-60周岁；<\/p> <p style=\"white-space: normal;\">2、个人信息真实详细；<\/p> <p style=\"white-space: normal;\">3、手机号填写常用号码；<\/p> <p style=\"white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);\">     <span style=\"color: rgb(255, 0, 0);\">4、住宅地址和单位地址必须在同一城市；<\/span> <\/p> <p style=\"white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);\">     <span style=\"color: rgb(255, 0, 0);\">5、住宅地址和单位地址必须详细到门牌号，例xx路xx号创新大厦A座505；<\/span> <\/p> <p style=\"white-space: normal;\">6、单位座机为必填项；<\/p> <p style=\"white-space: normal;\">7、家庭座机填写可提高通过率，务必记住号码；<\/p> <p style=\"white-space: normal;\">8、提交成功后，可在银行官网查询进度。<\/p> <p>     <br/> <\/p>","settlement_status":"1","apply_false_number":"10112","apply_true_number":"6089","success_false_number":"5741","success_true_number":"0","je_amount":"15000.00","max_amount":"200000.00","tag":"可秒批","status":"1","is_recommend":"1","hit":"13075","sort":"1","create_time":"2019-04-28 17:47:33","update_time":"2019-06-03 09:38:58","create_user_id":"13","update_user_id":"0","url_id":"349","shqc":"1","shqc_goods_id":"0","is_store_show":"1","baffle_img":"","settlement_cycle":"","feedback_loop":"5天自动结算","is_feedback":"0","apply_log":"/20190508/1394291557306215815850.png","co_amount":"180.00","settlement_state":"新户下卡"}],"img":"/card_index_img_new.jpg"}
     */

    private int code;
    private String message;
    private String response_time;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse_time() {
        return response_time;
    }

    public void setResponse_time(String response_time) {
        this.response_time = response_time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * page : {"totalPage":3,"totalCount":"23"}
         * list : [{"id":"51","name":"浦发银行梦卡","cooperative_bacooperative_bank_id":"141","logo_img":"/20190429/8531711556517863626123.png","annual_fee_status":"1","annual_fee":"终身免年费","probability":"0","abstract":"天天5折享美食，更有日日优惠福利券","characteristic":" <p>1、浦发梦卡终身免年费。<\/p> <p>2、浦发Visa梦卡是浦发银行首张符合EMV标准且具有Visa payWave功能的芯片信用卡，采用\"芯片+磁条\"的形式，不仅具有传统的磁条识别系统,还采用了高度集成的芯片作为信息载体,为信用卡的安全和功能拓展奠定了坚实的基础，为您带来更好的支付体验。<\/p> <p>3、用卡更安全：采用高度集成的芯片为载体，保障您的用卡安全。<\/p> <p>4、支付更便捷：只需在收银台的感应式卡片阅读机前感应卡片，即可轻松又安全的完成付款。<\/p>","raiders":" <p style=\"white-space: normal;\">1、年龄要求：18-60周岁；<\/p> <p style=\"white-space: normal;\">2、个人信息真实详细；<\/p> <p style=\"white-space: normal;\">3、手机号填写常用号码；<\/p> <p style=\"white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);\">     <span style=\"color: rgb(255, 0, 0);\">4、住宅地址和单位地址必须在同一城市；<\/span> <\/p> <p style=\"white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);\">     <span style=\"color: rgb(255, 0, 0);\">5、住宅地址和单位地址必须详细到门牌号，例xx路xx号创新大厦A座505；<\/span> <\/p> <p style=\"white-space: normal;\">6、单位座机为必填项；<\/p> <p style=\"white-space: normal;\">7、家庭座机填写可提高通过率，务必记住号码；<\/p> <p style=\"white-space: normal;\">8、提交成功后，可在银行官网查询进度。<\/p> <p>     <br/> <\/p>","settlement_status":"1","apply_false_number":"10112","apply_true_number":"6089","success_false_number":"5741","success_true_number":"0","je_amount":"15000.00","max_amount":"200000.00","tag":"可秒批","status":"1","is_recommend":"1","hit":"13075","sort":"1","create_time":"2019-04-28 17:47:33","update_time":"2019-06-03 09:38:58","create_user_id":"13","update_user_id":"0","url_id":"349","shqc":"1","shqc_goods_id":"0","is_store_show":"1","baffle_img":"","settlement_cycle":"","feedback_loop":"5天自动结算","is_feedback":"0","apply_log":"/20190508/1394291557306215815850.png","co_amount":"180.00","settlement_state":"新户下卡"}]
         * img : /card_index_img_new.jpg
         */

        private PageBean page;
        private String img;
        private List<ListBean> list;

        public PageBean getPage() {
            return page;
        }

        public void setPage(PageBean page) {
            this.page = page;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PageBean {
            /**
             * totalPage : 3
             * totalCount : 23
             */

            private int totalPage;
            private String totalCount;

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }
        }

        public static class ListBean {
            /**
             * id : 51
             * name : 浦发银行梦卡
             * cooperative_bacooperative_bank_id : 141
             * logo_img : /20190429/8531711556517863626123.png
             * annual_fee_status : 1
             * annual_fee : 终身免年费
             * probability : 0
             * abstract : 天天5折享美食，更有日日优惠福利券
             * characteristic :  <p>1、浦发梦卡终身免年费。</p> <p>2、浦发Visa梦卡是浦发银行首张符合EMV标准且具有Visa payWave功能的芯片信用卡，采用"芯片+磁条"的形式，不仅具有传统的磁条识别系统,还采用了高度集成的芯片作为信息载体,为信用卡的安全和功能拓展奠定了坚实的基础，为您带来更好的支付体验。</p> <p>3、用卡更安全：采用高度集成的芯片为载体，保障您的用卡安全。</p> <p>4、支付更便捷：只需在收银台的感应式卡片阅读机前感应卡片，即可轻松又安全的完成付款。</p>
             * raiders :  <p style="white-space: normal;">1、年龄要求：18-60周岁；</p> <p style="white-space: normal;">2、个人信息真实详细；</p> <p style="white-space: normal;">3、手机号填写常用号码；</p> <p style="white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);">     <span style="color: rgb(255, 0, 0);">4、住宅地址和单位地址必须在同一城市；</span> </p> <p style="white-space: normal; text-decoration-line: underline; color: rgb(250, 134, 54);">     <span style="color: rgb(255, 0, 0);">5、住宅地址和单位地址必须详细到门牌号，例xx路xx号创新大厦A座505；</span> </p> <p style="white-space: normal;">6、单位座机为必填项；</p> <p style="white-space: normal;">7、家庭座机填写可提高通过率，务必记住号码；</p> <p style="white-space: normal;">8、提交成功后，可在银行官网查询进度。</p> <p>     <br/> </p>
             * settlement_status : 1
             * apply_false_number : 10112
             * apply_true_number : 6089
             * success_false_number : 5741
             * success_true_number : 0
             * je_amount : 15000.00
             * max_amount : 200000.00
             * tag : 可秒批
             * status : 1
             * is_recommend : 1
             * hit : 13075
             * sort : 1
             * create_time : 2019-04-28 17:47:33
             * update_time : 2019-06-03 09:38:58
             * create_user_id : 13
             * update_user_id : 0
             * url_id : 349
             * shqc : 1
             * shqc_goods_id : 0
             * is_store_show : 1
             * baffle_img :
             * settlement_cycle :
             * feedback_loop : 5天自动结算
             * is_feedback : 0
             * apply_log : /20190508/1394291557306215815850.png
             * co_amount : 180.00
             * settlement_state : 新户下卡
             */

            private String id;
            private String name;
            private String cooperative_bacooperative_bank_id;
            private String logo_img;
            private String annual_fee_status;
            private String annual_fee;
            private String probability;
            @SerializedName("abstract")
            private String abstractX;
            private String characteristic;
            private String raiders;
            private String settlement_status;
            private String apply_false_number;
            private String apply_true_number;
            private String success_false_number;
            private String success_true_number;
            private String je_amount;
            private String max_amount;
            private String tag;
            private String status;
            private String is_recommend;
            private String hit;
            private String sort;
            private String create_time;
            private String update_time;
            private String create_user_id;
            private String update_user_id;
            private String url_id;
            private String shqc;
            private String shqc_goods_id;
            private String is_store_show;
            private String baffle_img;
            private String settlement_cycle;
            private String feedback_loop;
            private String is_feedback;
            private String apply_log;
            private String co_amount;
            private String settlement_state;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCooperative_bacooperative_bank_id() {
                return cooperative_bacooperative_bank_id;
            }

            public void setCooperative_bacooperative_bank_id(String cooperative_bacooperative_bank_id) {
                this.cooperative_bacooperative_bank_id = cooperative_bacooperative_bank_id;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getAnnual_fee_status() {
                return annual_fee_status;
            }

            public void setAnnual_fee_status(String annual_fee_status) {
                this.annual_fee_status = annual_fee_status;
            }

            public String getAnnual_fee() {
                return annual_fee;
            }

            public void setAnnual_fee(String annual_fee) {
                this.annual_fee = annual_fee;
            }

            public String getProbability() {
                return probability;
            }

            public void setProbability(String probability) {
                this.probability = probability;
            }

            public String getAbstractX() {
                return abstractX;
            }

            public void setAbstractX(String abstractX) {
                this.abstractX = abstractX;
            }

            public String getCharacteristic() {
                return characteristic;
            }

            public void setCharacteristic(String characteristic) {
                this.characteristic = characteristic;
            }

            public String getRaiders() {
                return raiders;
            }

            public void setRaiders(String raiders) {
                this.raiders = raiders;
            }

            public String getSettlement_status() {
                return settlement_status;
            }

            public void setSettlement_status(String settlement_status) {
                this.settlement_status = settlement_status;
            }

            public String getApply_false_number() {
                return apply_false_number;
            }

            public void setApply_false_number(String apply_false_number) {
                this.apply_false_number = apply_false_number;
            }

            public String getApply_true_number() {
                return apply_true_number;
            }

            public void setApply_true_number(String apply_true_number) {
                this.apply_true_number = apply_true_number;
            }

            public String getSuccess_false_number() {
                return success_false_number;
            }

            public void setSuccess_false_number(String success_false_number) {
                this.success_false_number = success_false_number;
            }

            public String getSuccess_true_number() {
                return success_true_number;
            }

            public void setSuccess_true_number(String success_true_number) {
                this.success_true_number = success_true_number;
            }

            public String getJe_amount() {
                return je_amount;
            }

            public void setJe_amount(String je_amount) {
                this.je_amount = je_amount;
            }

            public String getMax_amount() {
                return max_amount;
            }

            public void setMax_amount(String max_amount) {
                this.max_amount = max_amount;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_recommend() {
                return is_recommend;
            }

            public void setIs_recommend(String is_recommend) {
                this.is_recommend = is_recommend;
            }

            public String getHit() {
                return hit;
            }

            public void setHit(String hit) {
                this.hit = hit;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }

            public String getCreate_user_id() {
                return create_user_id;
            }

            public void setCreate_user_id(String create_user_id) {
                this.create_user_id = create_user_id;
            }

            public String getUpdate_user_id() {
                return update_user_id;
            }

            public void setUpdate_user_id(String update_user_id) {
                this.update_user_id = update_user_id;
            }

            public String getUrl_id() {
                return url_id;
            }

            public void setUrl_id(String url_id) {
                this.url_id = url_id;
            }

            public String getShqc() {
                return shqc;
            }

            public void setShqc(String shqc) {
                this.shqc = shqc;
            }

            public String getShqc_goods_id() {
                return shqc_goods_id;
            }

            public void setShqc_goods_id(String shqc_goods_id) {
                this.shqc_goods_id = shqc_goods_id;
            }

            public String getIs_store_show() {
                return is_store_show;
            }

            public void setIs_store_show(String is_store_show) {
                this.is_store_show = is_store_show;
            }

            public String getBaffle_img() {
                return baffle_img;
            }

            public void setBaffle_img(String baffle_img) {
                this.baffle_img = baffle_img;
            }

            public String getSettlement_cycle() {
                return settlement_cycle;
            }

            public void setSettlement_cycle(String settlement_cycle) {
                this.settlement_cycle = settlement_cycle;
            }

            public String getFeedback_loop() {
                return feedback_loop;
            }

            public void setFeedback_loop(String feedback_loop) {
                this.feedback_loop = feedback_loop;
            }

            public String getIs_feedback() {
                return is_feedback;
            }

            public void setIs_feedback(String is_feedback) {
                this.is_feedback = is_feedback;
            }

            public String getApply_log() {
                return apply_log;
            }

            public void setApply_log(String apply_log) {
                this.apply_log = apply_log;
            }

            public String getCo_amount() {
                return co_amount;
            }

            public void setCo_amount(String co_amount) {
                this.co_amount = co_amount;
            }

            public String getSettlement_state() {
                return settlement_state;
            }

            public void setSettlement_state(String settlement_state) {
                this.settlement_state = settlement_state;
            }
        }
    }
}
