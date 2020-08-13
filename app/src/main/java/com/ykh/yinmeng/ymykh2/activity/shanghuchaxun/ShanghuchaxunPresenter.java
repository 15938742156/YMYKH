package com.ykh.yinmeng.ymykh2.activity.shanghuchaxun;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ShanghuchaxunPresenter<V extends ShanghuchaxunMVPView> extends IMVPPresenter<V> {
    void setType(int type);
    void onSousuoBtn(String keywords);
}
