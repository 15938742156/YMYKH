package com.ykh.yinmeng.ymykh2.fragment.machine;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface MachineFragmentPresenter<V extends MachineFragmentMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
