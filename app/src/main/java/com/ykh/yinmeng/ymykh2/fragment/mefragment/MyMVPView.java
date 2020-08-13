package com.ykh.yinmeng.ymykh2.fragment.mefragment;


import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface MyMVPView extends IMVPView {

    void showNameSuccess(String name);
    void showTelSuccess(String tel);
    void showToast(String msg);
    void showUserHeadImg(Bitmap headBitmap);
    void showDialog();
    void dismissDialog();

}
