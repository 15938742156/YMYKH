package com.ykh.yinmeng.ymykh2.activity.kefuweixin;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface KefuPresenter<V extends KefuMVPView> extends IMVPPresenter<V> {

    void onToggleCopyBtn(Activity activity,int position);
}
