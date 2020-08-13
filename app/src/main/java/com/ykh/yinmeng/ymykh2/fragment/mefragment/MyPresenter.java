package com.ykh.yinmeng.ymykh2.fragment.mefragment;

import android.app.Activity;
import android.content.Context;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface MyPresenter<V extends MyMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void paiZhao(Activity activity);
    void xuanZeTuPian(Activity activity);
}
