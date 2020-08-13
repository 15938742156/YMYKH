package com.ykh.yinmeng.ymykh2.model;


import com.ykh.yinmeng.ymykh2.bean.CardBean;

import java.util.List;

public class CardResponse {

    /**
     * code : 1
     * msg : ok
     * data : [{"uid":"34109","id":9,"banksNumber":"1236543216541231111","banks":"中国银行","status":1}]
     */

    private int code;
    private String msg;
    private List<CardBean> data;

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

    public List<CardBean> getData() {
        return data;
    }

    public void setData(List<CardBean> data) {
        this.data = data;
    }


}
