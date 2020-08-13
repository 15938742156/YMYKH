package com.ykh.yinmeng.ymykh2.activity.itemcardgoods;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ItemGoodsPresenter<V extends ItemGoodsMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void onExchangeBt(Activity activity);
}
