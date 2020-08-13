package com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface ShanghuzicaiPresenter<V extends ShanghuzicaiMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onNumEditTextChange(CharSequence charSequence);
    void onBuyBtn(Activity activity,String num);
    void onToggleYaoqingBtn(Activity activity);
//    void onEditTextChange(CharSequence charSequence);
}
