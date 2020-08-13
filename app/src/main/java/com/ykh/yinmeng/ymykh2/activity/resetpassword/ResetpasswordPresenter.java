package com.ykh.yinmeng.ymykh2.activity.resetpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ResetpasswordPresenter<V extends ResetpasswordMVPView> extends IMVPPresenter<V> {

    void onToggleQuedingBtn(String tel, String yzm, String pwd);
    void onToggleCodeBtn(String phone);

}
