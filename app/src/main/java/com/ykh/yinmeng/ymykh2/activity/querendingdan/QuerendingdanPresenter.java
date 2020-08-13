package com.ykh.yinmeng.ymykh2.activity.querendingdan;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


/**
 * 2018/11/10 15:10
 * Description：
 */
public interface QuerendingdanPresenter<V extends QuerendingdanMVPView> extends IMVPPresenter<V> {
    void onInit(Activity activity);
    void onToggleSelectAddressBtn(Activity activity);
    void onToggleEnsureBtn(Activity activity);
    void onEditTextChange(CharSequence charSequence);
    void confirmPayPwd(String pwd);
    void getNowBalance();
    void onToggleBtn(int type);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
