package com.ykh.yinmeng.ymykh2.activity.jifentixian;

import android.app.Activity;
import android.content.Intent;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPPresenter;
import com.ykh.yinmeng.ymykh2.bean.CardBean;


public interface JifenTixianPresenter<V extends JifenTixianMVPView> extends IMVPPresenter<V> {

    void onInit(Activity activity);
    void onConfirmBtn(Activity activity);
    void updateCard(CardBean card);
    void onTextChanged(String text);
    void onToggleSelectCardBtn(Activity activity);
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void confirmPayPwd(String pwd);


}
