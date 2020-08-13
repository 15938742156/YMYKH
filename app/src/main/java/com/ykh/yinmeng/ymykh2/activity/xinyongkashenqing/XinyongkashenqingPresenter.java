package com.ykh.yinmeng.ymykh2.activity.xinyongkashenqing;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface XinyongkashenqingPresenter<V extends XinyongkashenqingMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
}
