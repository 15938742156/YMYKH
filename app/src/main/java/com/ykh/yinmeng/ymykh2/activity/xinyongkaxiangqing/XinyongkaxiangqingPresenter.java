package com.ykh.yinmeng.ymykh2.activity.xinyongkaxiangqing;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface XinyongkaxiangqingPresenter<V extends XinyongkaxiangqingMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onNext(Activity activity);
}
