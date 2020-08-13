package com.ykh.yinmeng.ymykh2.activity.yaoqinghuiyuan;

import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;


public interface YaoqinghuiyuanMVPView extends IMVPView {

    void getBackgroundSuccess(String img);
    void getcontextSuccess(Bitmap bitmap);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
    void showToast(String msg);
    void showShareView(String path);
}
