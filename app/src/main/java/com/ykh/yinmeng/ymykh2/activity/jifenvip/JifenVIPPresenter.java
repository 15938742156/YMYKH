package com.ykh.yinmeng.ymykh2.activity.jifenvip;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface JifenVIPPresenter<V extends JifenVIPMVPView> extends IMVPPresenter<V> {

    void OnChongzhiBtn(Activity activity,int type);
    void OnFreeBtn(Activity activity,String type);

}
