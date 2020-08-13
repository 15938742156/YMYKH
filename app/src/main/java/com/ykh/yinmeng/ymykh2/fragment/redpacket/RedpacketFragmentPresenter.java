package com.ykh.yinmeng.ymykh2.fragment.redpacket;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface RedpacketFragmentPresenter<V extends RedpacketFragmentMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
