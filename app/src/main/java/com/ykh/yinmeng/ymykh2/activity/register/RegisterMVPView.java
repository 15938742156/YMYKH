package com.ykh.yinmeng.ymykh2.activity.register;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/12 9:13
 * Description：
 */
public interface RegisterMVPView extends IMVPView {
    void registerSuccess();
    void registerFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();
}
