package com.ykh.yinmeng.ymykh2.activity.base;

public interface IMVPPresenter<V extends IMVPView> {
    void onAttach(V view);

    void onDetach();
}
