package com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface SetjiaoyipasswordMVPView extends IMVPView {
    void setjiaoyipasswordSuccess();
    void setjiaoyipasswordFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();
    void onTick(long millisUntilFinished);
    void onTickFinish();
}
