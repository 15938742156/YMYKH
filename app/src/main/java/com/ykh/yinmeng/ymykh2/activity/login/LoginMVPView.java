package com.ykh.yinmeng.ymykh2.activity.login;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 9:47
 * Description：
 */
public interface LoginMVPView extends IMVPView {
    void loginSuccess();
    void loginFailure(String msg);
    void showDialog();
    void dismissDialog();
    void autoLogin();
}
