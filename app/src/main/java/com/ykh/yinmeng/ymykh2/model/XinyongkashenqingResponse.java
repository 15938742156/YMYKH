package com.ykh.yinmeng.ymykh2.model;

public class XinyongkashenqingResponse {

    /**
     * code : 1
     * msg : 链接获取成功
     * data : http://open.olvip.vip/superbank-open/wx/creditcard!visa_application_step.action?orgId=672568&params=84112972889c66364282d20f01dbce2e1cba82dd98a1b89f9e6120fcbbc1e56d3c25f25ccc4b33406a34f269b071316ed9e1e4da0484a12ef902dc09e584eb14bb32b27197f8c26b35591bda8012c433
     */

    private int code;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
