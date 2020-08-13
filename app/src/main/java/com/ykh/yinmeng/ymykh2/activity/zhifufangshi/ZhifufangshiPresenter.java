package com.ykh.yinmeng.ymykh2.activity.zhifufangshi;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


/**
 * 2018/11/10 15:10
 * Description：
 */
public interface ZhifufangshiPresenter<V extends ZhifufangshiMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void onToggleEnsureBtn(Activity activity);
    void onWeixinBtn(Activity activity);
}
