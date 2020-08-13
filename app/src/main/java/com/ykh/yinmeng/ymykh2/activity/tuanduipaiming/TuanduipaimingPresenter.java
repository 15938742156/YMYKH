package com.ykh.yinmeng.ymykh2.activity.tuanduipaiming;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

/**
 * 2018/11/10 15:10
 * Description：
 */
public interface TuanduipaimingPresenter<V extends TuanduipaimingMVPView> extends IMVPPresenter<V> {
//    void onToggleCodeBtn(String phone);
    void getData();
}
