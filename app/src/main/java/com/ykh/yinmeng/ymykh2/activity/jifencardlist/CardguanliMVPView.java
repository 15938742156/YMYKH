package com.ykh.yinmeng.ymykh2.activity.jifencardlist;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.CardBean;

import java.util.List;

public interface CardguanliMVPView extends IMVPView {

    void getListSuccess(List<CardBean> cardlist);
    void getListFailure(String msg);
    void delCardSuccess();
    void delCardFailure(String msg);
    void onItemRemoved(int position);
    void showDialog();
    void dismissDialog();
}
