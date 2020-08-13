package com.ykh.yinmeng.ymykh2.activity.tuiguangshouyi;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface TuiguangshouyiMVPView extends IMVPView {
    void getDataSuccess();
    void getDataFailure(String msg);
    void showChartView(String[] xLabel, String[] data);
    void showUserAccount(String msg);//我的所有会员 当月新增会员数  三级
    void showActiveShop(String msg);//我的所有会员 当月新增商户数  三级
    void showActiveCount(String msg);//总的收益(全部的)
    void showZSumAccount(String msg);//我的直营会员  当月激活商户数量
    void showActiveZShop(String msg);//我的直营会员 今天激活的商户数量
    void showDialog();
    void dismissDialog();
}
