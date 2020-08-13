package com.ykh.yinmeng.ymykh2.fragment.orderdikoujilu;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.OrderdikoujiluResponse;
import com.ykh.yinmeng.ymykh2.model.TixianjiluResponse;

import java.util.List;

public interface OrderdikoujiluMVPView extends IMVPView {

    void getListSuccess(List<OrderdikoujiluResponse.DataBean> tixianjiluList);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();

}
