package com.ykh.yinmeng.ymykh2.activity.quick;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/10 15:10
 * Description：
 */
public interface QuickPresenter<V extends QuickMVPView> extends IMVPPresenter<V> {
    void onToggleLoginBtn(String username, String code);
    void onToggleCodeBtn(String phone);
}
