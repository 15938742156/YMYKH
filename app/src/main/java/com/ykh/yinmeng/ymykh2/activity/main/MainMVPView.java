package com.ykh.yinmeng.ymykh2.activity.main;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface MainMVPView extends IMVPView {

    void showNumSuccess(int num,String money);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}
