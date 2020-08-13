package com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface XinshoujiangtangxiangqingMVPView extends IMVPView {

    void getUrlSuccess(String url);
    void showDialog();
    void dismissDialog();
    void showShareView(String title, String text, String url);
    void getTitleSuccess(String title);
    void getTextSuccess(String text);
}
