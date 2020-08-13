package com.ykh.yinmeng.ymykh2.activity.addressadd;

import android.app.Activity;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface AddaddressPresenter<V extends AddaddressMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onToggleAddBtn(String shrName, String shrMobile, String shrProvince, String shrCity, String shrArea, String shrAddress, String isDefault);
    void onToggleCityPickerBtn(Activity activity);
}
