package com.ykh.yinmeng.ymykh2.activity.resetloginpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface ResetLoginpasswordMVPView extends IMVPView {
    void modifySuccess();
    void modifyFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();
}
