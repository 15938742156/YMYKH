package com.ykh.yinmeng.ymykh2.activity.setLoginpassword;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface SetLoginpasswordPresenter<V extends SetLoginpasswordMVPView> extends IMVPPresenter<V> {
    void onToggleModifyBtn(String password);
}
