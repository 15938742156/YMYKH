package com.ykh.yinmeng.ymykh2.activity.jifenshouzhi;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface JifenshouzhiPresenter<V extends JifenshouzhiMVPView> extends IMVPPresenter<V> {
    void onsTimeBt(Activity activity);
    void oneTimeBt(Activity activity);
    void onConfirm(String startDate, String endDate);
}
