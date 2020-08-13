package com.ykh.yinmeng.ymykh2.activity.logistics;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface LogisticsPresenter<V extends LogisticsMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);

}
