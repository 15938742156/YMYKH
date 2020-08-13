package com.ykh.yinmeng.ymykh2.activity.resetpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface ResetpasswordMVPView extends IMVPView {

    void resetjiaoyipasswordSuccess();
    void resetjiaoyipasswordFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();

}
