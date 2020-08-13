package com.ykh.yinmeng.ymykh2.activity.shiming;

import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;


public interface ShimingMVPView extends IMVPView {

    void showZhengMian(Bitmap bitmap);
    void showFanMian(Bitmap bitmap);
    void showDialog();
    void dismissDialog();
    void showAlert(String title, String message);
}
