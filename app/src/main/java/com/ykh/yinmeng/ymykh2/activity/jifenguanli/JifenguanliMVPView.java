package com.ykh.yinmeng.ymykh2.activity.jifenguanli;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;

import java.util.List;

public interface JifenguanliMVPView extends IMVPView {

    void showCoinSuccess(String coin);
    void showInCoinSuccess(String incoin);
    void showOutCoinSuccess(String outcoin);
    void showInRankSuccess(String inrank);
    void getStatusSuccess(int status);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}
