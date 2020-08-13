package com.ykh.yinmeng.ymykh2.activity.mymember;


import android.view.View;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/10 15:10
 * Description：
 */
public interface MymemberPresenter<V extends MymemberMVPView> extends IMVPPresenter<V> {
    void onToggleSpinnerBtn(View view);
    void  getData();
}
