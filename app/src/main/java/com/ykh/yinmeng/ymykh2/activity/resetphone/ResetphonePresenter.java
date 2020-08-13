package com.ykh.yinmeng.ymykh2.activity.resetphone;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ResetphonePresenter <V extends ResetphoneMVPView> extends IMVPPresenter<V> {
    void onToggleModifyBtn(String newtel, String yzm);
    void onToggleCodeBtn(String phone);
}
