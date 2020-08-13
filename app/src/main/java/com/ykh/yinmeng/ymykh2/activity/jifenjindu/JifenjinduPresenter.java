package com.ykh.yinmeng.ymykh2.activity.jifenjindu;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface JifenjinduPresenter<V extends JifenjinduMVPView> extends IMVPPresenter<V> {
    void onsTimeBt(Activity activity);
    void oneTimeBt(Activity activity);
    void onConfirm(String startDate,String endDate);
}
