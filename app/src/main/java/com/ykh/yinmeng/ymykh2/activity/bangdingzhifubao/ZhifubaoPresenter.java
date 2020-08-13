package com.ykh.yinmeng.ymykh2.activity.bangdingzhifubao;


import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ZhifubaoPresenter<V extends ZhifubaoMVPView> extends IMVPPresenter<V> {
    void onToggleModifyBtn(Activity activity, String name, String tel, String yzm);
    void onToggleCodeBtn(String phone);
}
