package com.ykh.yinmeng.ymykh2.activity.yaoqing;

import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.BackgroundResponse;

import java.util.List;


public interface YaoqingMVPView extends IMVPView {

    void getDataFailure(String msg);
    void showDialog();
    void dismissDialog();
    void getListSuccess(List<BackgroundResponse.DataBean> backlist);
}
