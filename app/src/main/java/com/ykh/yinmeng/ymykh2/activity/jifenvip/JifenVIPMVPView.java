package com.ykh.yinmeng.ymykh2.activity.jifenvip;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface JifenVIPMVPView extends IMVPView {

    void showVIPHuangjinSuccess(String viphaungjin);
    void showVIPzuanshiSuccess(String vipzuanshi);
    void getVIPSuccess(String msg);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();

}
