package com.ykh.yinmeng.ymykh2.activity.address;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;

import java.util.List;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface AddressMVPView extends IMVPView {
    void getListSuccess(List<AddressBean> addressList);
    void getListFailure(String msg);
    void delAddressSuccess();
    void delAddressFailure(String msg);
    void onItemRemoved(int position);
    void showDialog();
    void dismissDialog();
}
