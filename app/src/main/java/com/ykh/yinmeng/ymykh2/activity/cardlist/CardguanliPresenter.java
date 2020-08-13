package com.ykh.yinmeng.ymykh2.activity.cardlist;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface CardguanliPresenter<V extends CardguanliMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleTianjiaBtn(Activity activity);
    void onToggleDeleteBtn(int position);
    void onToggleEditBtn(Activity activity, int position);
    void onToggleItem(Activity activity, int position);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
