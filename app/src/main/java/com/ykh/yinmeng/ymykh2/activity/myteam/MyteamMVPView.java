package com.ykh.yinmeng.ymykh2.activity.myteam;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface MyteamMVPView extends IMVPView {
    void getDataSuccess();
    void getDataFailure(String msg);
    void showSumDeal(String msg);
    void showDayUser(String msg);
    void showZShop(String msg);
    void showZJqCount(String msg);
    void showActiveCount(String msg);
    void showInfo(String msg);
    void showDialog();
    void dismissDialog();
}
