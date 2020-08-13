package com.ykh.yinmeng.ymykh2.activity.jifensubmit;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface SubmitPresenter<V extends SubmitMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void paiZhao(Activity activity);
    void xuanZeTuPian(Activity activity);
    void onToggleItem(Activity activity, int position);
    void onExchangeBt(Activity activity, String gid,String sn,String converPic,String mark);
}
