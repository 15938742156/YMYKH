package com.ykh.yinmeng.ymykh2.activity.yaoqing;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface YaoqingPresenter<V extends YaoqingMVPView> extends IMVPPresenter<V> {

    void onToggleItem(Activity activity,int position);
}
