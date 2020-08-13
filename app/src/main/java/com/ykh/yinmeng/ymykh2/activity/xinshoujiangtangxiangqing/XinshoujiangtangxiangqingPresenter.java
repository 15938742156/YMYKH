package com.ykh.yinmeng.ymykh2.activity.xinshoujiangtangxiangqing;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface XinshoujiangtangxiangqingPresenter<V extends XinshoujiangtangxiangqingMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleYaoqingBtn(Activity activity);
}
