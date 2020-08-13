package com.ykh.yinmeng.ymykh2.activity.zhifufangshi;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface ZhifufangshiMVPView extends IMVPView {
    void paySuccess(String msg);
    void showErro(String msg);
    void showPayMoney(String money);
    void showDialog();
    void dismissDialog();
}
