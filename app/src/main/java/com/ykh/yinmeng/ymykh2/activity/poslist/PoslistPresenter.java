package com.ykh.yinmeng.ymykh2.activity.poslist;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface PoslistPresenter<V extends PoslistMVPView> extends IMVPPresenter<V> {

    void refreshData();
    void onToggleItem(Activity activity, int position);
}
