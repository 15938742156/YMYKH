package com.ykh.yinmeng.ymykh2.activity.xinyongkajindu;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface XinyongkajinduMVPView extends IMVPView {

    void getUrlSuccess(String url);
    void getUrlFailure(String msg);
    void showDialog();
    void dismissDialog();
}
