package com.ykh.yinmeng.ymykh2.activity.itemcard;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ItemCardPresenter<V extends ItemCardMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void onToggleItem(Activity activity, int position);
    void onBtnCount(String count);
}
