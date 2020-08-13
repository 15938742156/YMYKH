package com.ykh.yinmeng.ymykh2.activity.addressadd;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;

public interface AddaddressMVPView extends IMVPView {

    void setShrName(String shrName);
    void setShrMobile(String shrMobile);
    void setShrProvince(String shrProvince);
    void setShrCity(String shrCity);
    void setShrArea(String shrArea);
    void setShrAddress(String shrAddress);
    void setIsDefault(boolean isDefault);
    void addAdressSuccess();
    void addAdressFailure(String msg);
    void editAdressSuccess();
    void editAdressFailure(String msg);
    void showDialog();
    void dismissDialog();

}
