package com.ykh.yinmeng.ymykh2.activity.forget;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/12 9:14
 * Description：
 */
public interface ForgetPresenter<V extends ForgetMVPView> extends IMVPPresenter<V> {
    void onToggleModifyBtn(String tel, String password, String yzm);
    void onToggleCodeBtn(String phone);
}
