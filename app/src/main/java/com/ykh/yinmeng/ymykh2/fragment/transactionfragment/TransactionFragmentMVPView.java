package com.ykh.yinmeng.ymykh2.fragment.transactionfragment;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;

import java.util.List;

public interface TransactionFragmentMVPView extends IMVPView {

    void getListSuccess(List<RebateResponse.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
