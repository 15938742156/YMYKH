package com.ykh.yinmeng.ymykh2.activity.shanghuchaxun;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.ShanghuchaxunResponse;

import java.util.List;

public interface ShanghuchaxunMVPView extends IMVPView {
    void getListSuccess(List<ShanghuchaxunResponse.DataBean> datalist);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}
