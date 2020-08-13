package com.ykh.yinmeng.ymykh2.activity.credits;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;

import java.util.List;

public interface CreditsMVPView extends IMVPView {

    void getListSuccess(List<CreditsResponse.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
