package com.ykh.yinmeng.ymykh2.fragment.machine;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;

import java.util.List;


public interface MachineFragmentMVPView extends IMVPView {

    void getListSuccess(List<RebateResponse.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
