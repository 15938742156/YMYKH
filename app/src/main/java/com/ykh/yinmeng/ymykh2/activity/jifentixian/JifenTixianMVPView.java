package com.ykh.yinmeng.ymykh2.activity.jifentixian;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.CardBean;

public interface JifenTixianMVPView extends IMVPView {

    void updateCard(CardBean card);
    void getMoneySuccess(String money);
    void getDataSuccess(String msg);
    void getDataFailure(String msg);
    void onPwdCorrect();//密码正确
    void onPwdErro(String msg);//交易密码错误
    void showDialog();
    void dismissDialog();


}
