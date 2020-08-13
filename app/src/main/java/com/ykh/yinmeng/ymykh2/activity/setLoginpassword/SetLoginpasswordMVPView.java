package com.ykh.yinmeng.ymykh2.activity.setLoginpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface SetLoginpasswordMVPView extends IMVPView {
    void modifySuccess();
    void modifyFailure(String msg);
    void showDialog();
    void dismissDialog();
}
