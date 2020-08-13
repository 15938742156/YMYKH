package com.ykh.yinmeng.ymykh2.activity.jifenshouzhi;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenShouzhiResponse;

import java.util.List;

public interface JifenshouzhiMVPView extends IMVPView {

    void showsTimeSuccess(String time);
    void showeTimeSuccess(String time);
    void getListSuccess(List<JifenShouzhiResponse.DataBeanX.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
