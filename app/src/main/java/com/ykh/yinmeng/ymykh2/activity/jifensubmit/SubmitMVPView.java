package com.ykh.yinmeng.ymykh2.activity.jifensubmit;

import android.graphics.Bitmap;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;

import java.util.List;

public interface SubmitMVPView extends IMVPView {

    void getListSuccess(List<JifenItemgoodsResponse.DataBean> list);
    void getListFailure(String msg);
    void getTypeSuccess(int type);
    void showSubmitSuccess(String msg);
    void showSubmitFailure(String msg);
    void showUserHeadImg(Bitmap headBitmap);
    void showToast(String msg);
    void showDialog();
    void dismissDialog();
}
