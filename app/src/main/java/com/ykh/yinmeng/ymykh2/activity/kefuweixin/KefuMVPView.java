package com.ykh.yinmeng.ymykh2.activity.kefuweixin;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.KefuResponse;

import java.util.List;

public interface KefuMVPView extends IMVPView {
    void getListSuccess(List<String> list);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();

}
