package com.ykh.yinmeng.ymykh2.model;

import java.util.List;

public class KefuResponse {

    /**
     * code : 1
     * msg : 客服微信
     * data : ["sdfdsf","sdfsdfsd","dsfsdfsd"]
     */

    private int code;
    private String msg;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
