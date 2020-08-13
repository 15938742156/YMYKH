package com.ykh.yinmeng.ymykh2.activity.login;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/10 9:49
 * Description：
 */
public interface LoginPresenter<V extends LoginMVPView> extends IMVPPresenter<V> {
    void onToggleLoginBtn(String username, String passwd);
}
