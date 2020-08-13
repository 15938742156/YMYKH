package com.ykh.yinmeng.ymykh2.fragment.homefragment;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


public interface HomeFragmentPresenter<V extends HomeFragmentMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onXiangQingBtn(Activity activity);
    void onToggleItem(Activity activity);
    void onToggleNotice(Activity activity, int position);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
