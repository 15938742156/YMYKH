package com.ykh.yinmeng.ymykh2.activity.logistics;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.LogisticsResponse;

import java.util.List;

public interface LogisticsMVPView extends IMVPView {
    void showType(String type);
    void showYunSucess(String yun);
    void getWaybill(String waybill);
    void getLogoSuccess(String logo);
    void getNameSuccess(String name);
    void getpriceSuccess(String price);
    void getNumSuccess(String numStr);
    void gettotalmoneySuccess(String money);
    void getAddressSuccess(String address);
    void getWuliuScccess(List<LogisticsResponse.TracesBean> wuliuList);
    void getWuliuFailure(String msg);
    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
}