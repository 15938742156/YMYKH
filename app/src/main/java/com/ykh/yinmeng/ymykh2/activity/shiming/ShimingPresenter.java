package com.ykh.yinmeng.ymykh2.activity.shiming;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface ShimingPresenter<V extends ShimingMVPView> extends IMVPPresenter<V> {

    void onToggleZhengMian(Activity activity);
    void onToggleFanMian(Activity activity);
    void onToggleNext(Activity activity);
    void onXieYiShiming(Activity activity);
    void onXieYiYinsi(Activity activity);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
