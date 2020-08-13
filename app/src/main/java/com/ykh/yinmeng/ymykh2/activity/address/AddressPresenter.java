package com.ykh.yinmeng.ymykh2.activity.address;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


/**
 * 2018/11/10 15:10
 * Description：
 */
public interface AddressPresenter<V extends AddressMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void onToggleTianjiaBtn(Activity activity);
    void onToggleDeleteBtn(int position);
    void onToggleEditBtn(Activity activity, int position);
    void onToggleItem(Activity activity, int position);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
