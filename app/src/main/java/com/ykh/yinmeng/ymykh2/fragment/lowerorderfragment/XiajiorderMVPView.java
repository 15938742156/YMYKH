package com.ykh.yinmeng.ymykh2.fragment.lowerorderfragment;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.XiajiorderResponse;

import java.util.List;

public interface XiajiorderMVPView extends IMVPView {
    void getDataSuccess(List<XiajiorderResponse.DataBeanX.DataBean> xiajiorderList);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();

}
