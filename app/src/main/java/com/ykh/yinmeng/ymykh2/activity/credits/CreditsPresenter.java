package com.ykh.yinmeng.ymykh2.activity.credits;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface CreditsPresenter<V extends CreditsMVPView> extends IMVPPresenter<V> {

    void onToggleTianjiaBtn(Activity activity);
    void onToggleItem(Activity activity, int position);

}
