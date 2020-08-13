package com.ykh.yinmeng.ymykh2.fragment.transactionfragment;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface TransactionFragmentPresenter<V extends TransactionFragmentMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onActivityResult(int resultCodequestCode, int resultCode, Intent data);
}
