package com.ykh.yinmeng.ymykh2.activity.yaoqinghuiyuan;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface YaoqinghuiyuanPresenter<V extends YaoqinghuiyuanMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleYaoqingBtn(Activity activity);
}
