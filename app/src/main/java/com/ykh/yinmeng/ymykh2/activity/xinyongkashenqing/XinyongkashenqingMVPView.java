package com.ykh.yinmeng.ymykh2.activity.xinyongkashenqing;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface XinyongkashenqingMVPView extends IMVPView {

    void getUrlSuccess(String url);
    void getUrlFailure(String msg);
    void showDialog();
    void dismissDialog();
}
