package com.ykh.yinmeng.ymykh2.activity.shanghutuozhan;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface ShanghutuozhanMVPView extends IMVPView {
    void modifySuccess();
    void modifyFailure(String msg);
    void getYzmSuccess();
    void getYzmFailure(String msg);
    void showDialog();
    void dismissDialog();

}
