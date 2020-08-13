package com.ykh.yinmeng.ymykh2.activity.itemcard;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;

import java.util.List;

public interface ItemCardMVPView extends IMVPView {

    void getTitleSuccess(String title);
    void showCountSuccess(String count);
    void getListSuccess(List<ItemcardResponse.DataBean> list);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
