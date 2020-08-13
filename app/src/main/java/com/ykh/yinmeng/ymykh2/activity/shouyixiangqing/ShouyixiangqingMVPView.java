package com.ykh.yinmeng.ymykh2.activity.shouyixiangqing;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface ShouyixiangqingMVPView extends IMVPView {
    void getDataSuccess();
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}
