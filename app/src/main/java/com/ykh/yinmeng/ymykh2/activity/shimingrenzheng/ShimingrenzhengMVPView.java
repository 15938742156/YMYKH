package com.ykh.yinmeng.ymykh2.activity.shimingrenzheng;

import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;


public interface ShimingrenzhengMVPView extends IMVPView {

    void showZhengMian(Bitmap bitmap);
    void showFanMian(Bitmap bitmap);
    void showTel(String tel);
    void showName(String name);
    void showCard(String card);
    void shimingSuccess();
    void showAlert(String title, String message);
}
