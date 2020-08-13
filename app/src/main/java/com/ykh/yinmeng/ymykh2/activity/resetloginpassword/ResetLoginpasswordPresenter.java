package com.ykh.yinmeng.ymykh2.activity.resetloginpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ResetLoginpasswordPresenter<V extends ResetLoginpasswordMVPView> extends IMVPPresenter<V> {
    void onToggleModifyBtn(String tel, String yzm, String password);
    void onToggleCodeBtn(String phone);
}
