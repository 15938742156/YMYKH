package com.ykh.yinmeng.ymykh2.activity.register;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/12 9:14
 * Description：
 */
public interface RegisterPresenter<V extends RegisterMVPView> extends IMVPPresenter<V> {
    void onToggleRegisterBtn(String yqtel, String tel, String password, String yzm);
    void onToggleCodeBtn(String phone);
}
