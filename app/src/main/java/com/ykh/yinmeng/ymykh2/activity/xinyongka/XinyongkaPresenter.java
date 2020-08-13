package com.ykh.yinmeng.ymykh2.activity.xinyongka;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface XinyongkaPresenter<V extends XinyongkaMVPView> extends IMVPPresenter<V> {

//    void refreshData();
    void onToggleItem(Activity activity, int position);
    void Data(int page);
}
