package com.ykh.yinmeng.ymykh2.activity.forget;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/12 9:13
 * Description：
 */
public interface ForgetMVPView extends IMVPView {
    void modifySuccess();
    void modifyFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();
}
