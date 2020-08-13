package com.ykh.yinmeng.ymykh2.fragment.cashfragment;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface CashFragmentPresenter<V extends CashFragmentMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void getData();
}
