package com.ykh.yinmeng.ymykh2.activity.mydata;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface MydataMVPView extends IMVPView {

    void showNameSuccess(String name);
    void showTelSuccess(String tel);
    void showSexSuccess(String sex);
    void showIdNumber(String idNumber);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();

}
