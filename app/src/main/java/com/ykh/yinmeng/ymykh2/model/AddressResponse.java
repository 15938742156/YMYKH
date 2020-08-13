package com.ykh.yinmeng.ymykh2.model;

import com.ykh.yinmeng.ymykh2.bean.AddressBean;

import java.util.List;

/**
 * Created At 2018/12/7 1:10.
 *
 * @author larry
 */
public class AddressResponse {

    /**
     * code : 1
     * msg : ok
     * data : [{"id":8,"uid":10020,"shrName":"刘莉妨","shrMobile":"15938742156","shrProvince":"河南省","shrCity":"郑州市","shrArea":"高新区","shrAddress":"八巷321号","isDefault":1,"createTime":"2018-11-30 01:32:40"}]
     */

    private int code;
    private String msg;
    private List<AddressBean> data;

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

    public List<AddressBean> getData() {
        return data;
    }

    public void setData(List<AddressBean> data) {
        this.data = data;
    }

}
