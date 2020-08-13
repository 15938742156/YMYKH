package com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


/**
 * 2018/11/10 15:10
 * Description：
 */
public interface SetjiaoyipasswordPresenter<V extends SetjiaoyipasswordMVPView> extends IMVPPresenter<V> {
    void onToggleQuedingBtn(Activity activity, String tel, String code, String pwd);
    void onToggleCodeBtn(String phone);
}
