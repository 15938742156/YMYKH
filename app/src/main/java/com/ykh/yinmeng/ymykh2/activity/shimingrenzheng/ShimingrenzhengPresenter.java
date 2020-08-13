package com.ykh.yinmeng.ymykh2.activity.shimingrenzheng;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface ShimingrenzhengPresenter<V extends ShimingrenzhengMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleNext(Activity activity);
    void shimingSuccess(Activity activity);
}
