package com.ykh.yinmeng.ymykh2.activity.quick;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface QuickMVPView extends IMVPView {
    void loginSuccess();
    void loginFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();
    void autoLogin();
}
