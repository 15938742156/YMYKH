package com.ykh.yinmeng.ymykh2.activity.poslist;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.ShanghuzicaiResponse;

import java.util.List;

public interface PoslistMVPView extends IMVPView {
    void getListSuccess(List<ShanghuzicaiResponse.DataBeanX.DataBean> jijulist);
    void getListFailure(String msg);
    void getAttentionSuccess();
    void getAttentionFailuye(String msg);
    void onItemRemoved(int position);
    void showDialog();
    void dismissDialog();
}
