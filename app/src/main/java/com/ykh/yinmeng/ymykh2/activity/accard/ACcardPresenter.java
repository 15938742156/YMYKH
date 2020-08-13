package com.ykh.yinmeng.ymykh2.activity.accard;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface ACcardPresenter<V extends ACcardMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleAddBtn(String bankName, String banksNumber, String branch, String status, String banksAccount);

}
