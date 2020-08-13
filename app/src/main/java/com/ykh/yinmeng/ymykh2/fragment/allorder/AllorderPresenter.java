package com.ykh.yinmeng.ymykh2.fragment.allorder;

import android.app.Activity;
import android.view.View;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;


/**
 * 2018/11/10 15:10
 * Description：
 */
public interface AllorderPresenter<V extends AllorderMVPView> extends IMVPPresenter<V> {
    void onToggleItem(Activity context, View view);
    void setType(int type);
    void getData();
}
