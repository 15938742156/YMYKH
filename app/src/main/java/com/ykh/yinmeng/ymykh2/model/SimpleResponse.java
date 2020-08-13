package com.ykh.yinmeng.ymykh2.model;

import java.io.Serializable;

/**
 * 2018/11/9 18:23
 * Description：
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int code;
    public String msg;

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.setCode(code);
        lzyResponse.setMsg(msg);
        return lzyResponse;
    }
}
