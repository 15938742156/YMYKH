package com.ykh.yinmeng.ymykh2.activity.itemcardgoods;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;

import java.util.List;

public interface ItemGoodsMVPView extends IMVPView {

    void getListSuccess(List<JifenItemgoodsResponse.DataBean> list);
    void getListFailure(String msg);
    void showImgSuccess(String img);
    void showDialog();
    void dismissDialog();
}
