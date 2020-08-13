package com.ykh.yinmeng.ymykh2.activity.jihuomingxi;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.JihuomingxiResponse;

import java.util.List;

public interface JihuomingxiMVPView extends IMVPView {
    void getListSuccess(List<JihuomingxiResponse.DataBean> jihuolist);
    void getListFailure(String msg);
    void showDialog();
    void dismissDialog();
}
