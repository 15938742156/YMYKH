package com.ykh.yinmeng.ymykh2.activity.xinyongkajindu;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface XinyongkajinduPresenter<V extends XinyongkajinduMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
}
