package com.ykh.yinmeng.ymykh2.fragment.redpacket;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;
import com.ykh.yinmeng.ymykh2.model.RedpacketResponse;

import java.util.List;


public interface RedpacketFragmentMVPView extends IMVPView {

    void getListSuccess(List<RedpacketResponse.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
