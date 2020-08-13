package com.ykh.yinmeng.ymykh2.fragment.myorderfragment;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.MyOrderResponse;

import java.util.List;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface MyorderMVPView extends IMVPView {
    void getDataSuccess(List<MyOrderResponse.DataBeanX.OrderBean> allorderList);
    void showFailureMsg(String msg);
    void notifyDataSetChanged();
    void notifyItemRemoved(int position);
    void showDialog();
    void showTixing(String tixing);
    void dismissDialog();
}
