package com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface ShanghuzicaiMVPView extends IMVPView {

    void getUrlSuccess(String url);
    void getUrlFailure(String msg);
    void getTitleSuccess(String title);
    void getMoneySuccess(String money);
    void getYunSuccess(String yun);
    void getIdSuccess(String id);
    void showShareView(String title, String miaoshu, String url,String img);
    void showDialog();
    void dismissDialog();
}
