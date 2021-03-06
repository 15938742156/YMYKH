package com.ykh.yinmeng.ymykh2.activity.accard;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface ACcardMVPView extends IMVPView {

    void setBankName(String bankName);
    void setBanksNumber(String banksNumber);
    void setBranch(String branch);
    void setStatus(boolean status);
    void setBankAccount(String bankAccount);
    void addBankcardSuccess();
    void addBankcardFailure(String msg);
    void editBankcardSuccess();
    void editBankcardFailure(String msg);
    void showDialog();
    void dismissDialog();
}
