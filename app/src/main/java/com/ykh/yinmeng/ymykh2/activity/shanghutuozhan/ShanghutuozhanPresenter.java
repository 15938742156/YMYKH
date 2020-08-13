package com.ykh.yinmeng.ymykh2.activity.shanghutuozhan;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;

public interface ShanghutuozhanPresenter<V extends  ShanghutuozhanMVPView> extends IMVPPresenter<V> {
    void onQuedingBtn(String tel, String inName, String sn);

}
