package com.ykh.yinmeng.ymykh2.model;

/**
 * Created At 2018/12/16 10:56.
 *
 * @author larry
 */
public class SubmitOrderResponse {

    /**
     * code : 1
     * msg : 订单提交成功！
     * data : {"is_pay":0,"out_trade_no":"YM1544928538424241000","pay_money":"120.00"}
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
         * is_pay : 0
         * out_trade_no : YM1544928538424241000
         * pay_money : 120.00
         */

        private int is_pay;
        public String out_trade_no;
        public String pay_money;

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }
    }
}
