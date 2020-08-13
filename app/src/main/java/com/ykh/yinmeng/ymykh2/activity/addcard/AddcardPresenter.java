package com.ykh.yinmeng.ymykh2.activity.addcard;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface AddcardPresenter<V extends AddcardMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleAddBtn(String bankAccount, String banksNumber, String bankName, String branch, String status);
    void onDelBtn(Activity activity);

}
