package com.ykh.yinmeng.ymykh2.activity.tixian;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.CardBean;

public interface TixianMVPView extends IMVPView {

    void updateCard(CardBean card);
    void getMoneySuccess(String money);
    void getDataSuccess(String msg);
    void getDataFailure(String msg);
    void onPwdCorrect();//密码正确
    void onPwdErro(String msg);//交易密码错误
    void OnZhifubaoSuccess(String msg);
    void OnZhifubaoFailure(String msg);
    void showDialog();
    void dismissDialog();


}
