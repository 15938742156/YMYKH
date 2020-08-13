package com.ykh.yinmeng.ymykh2.activity.xinyongka;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.NewXinyongkaResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkaResponse;

import java.util.List;

public interface XinyongkaMVPView extends IMVPView {
    void getListSuccess(List<NewXinyongkaResponse.DataBean.ListBean> shanghulist);
    void getListFailure(String msg);
    void getTotalpage(int page);
    void getAttentionSuccess();
    void getAttentionFailuye(String msg);
    void onItemRemoved(int position);
    void showDialog();
    void dismissDialog();
}
