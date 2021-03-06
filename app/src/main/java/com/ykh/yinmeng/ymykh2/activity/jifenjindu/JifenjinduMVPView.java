package com.ykh.yinmeng.ymykh2.activity.jifenjindu;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenjinduResponse;

import java.util.List;

public interface JifenjinduMVPView extends IMVPView {

    void showsTimeSuccess(String time);
    void showeTimeSuccess(String time);
    void getListSuccess(List<JifenjinduResponse.DataBeanX.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
