package com.ykh.yinmeng.ymykh2.activity.tuanduipaiming;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.TuanduipaimingResponse;

import java.util.List;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface TuanduipaimingMVPView extends IMVPView {
    void getDataSuccess(List<TuanduipaimingResponse.PaimingBean> paiminglist);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}
