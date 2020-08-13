package com.ykh.yinmeng.ymykh2.activity.mymember;


import android.view.View;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.MyhuiyuanResponse;

import java.util.List;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface MymemberMVPView extends IMVPView {
    void getDataSuccess(List<MyhuiyuanResponse.DataBean.LowInfoBean> list);
    void getDataFailure(String msg);
    void showTotalSumSuccess(MyhuiyuanResponse.DataBean.InfoBean infoBean);
    void showPopupMenu(MyhuiyuanResponse.DataBean.InfoBean infoBean, View view);
    void showDialog();
    void dismissDialog();
}
