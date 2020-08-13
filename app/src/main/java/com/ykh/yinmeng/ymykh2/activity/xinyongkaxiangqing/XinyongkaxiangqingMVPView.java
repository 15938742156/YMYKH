package com.ykh.yinmeng.ymykh2.activity.xinyongkaxiangqing;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface XinyongkaxiangqingMVPView extends IMVPView {

    void showNameSuccess(String name);
    void showAnnual_feeSuccess(String annual_fee);
    void showAbstractxSuccess(String abstractx);
    void showCharacteristicSuccess(String characteristic);
    void showRaidersSuccess(String raiders);
    void showJe_amountSuccess(String je_amount);
    void showMax_amountSuccess(String max_amount);
    void showTagSuccess(String tag);
    void showFeedback_loopSuccess(String feedback_loop);
    void showLogoSuccess(String logo);
    void showAmountSuccess(String amount);
    void showDialog();
    void dismissDialog();
}
