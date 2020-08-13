package com.ykh.yinmeng.ymykh2.fragment.tixianjilu;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.TixianjiluResponse;

import java.util.List;

public interface TixianjiluMVPView extends IMVPView {

    void getListSuccess(List<TixianjiluResponse.DataBean> tixianjiluList);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();

}
