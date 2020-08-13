package com.ykh.yinmeng.ymykh2.activity.querendingdan;

import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.bean.POSStoreBean;

/**
 * 2018/11/10 15:04
 * Description：
 */
public interface QuerendingdanMVPView extends IMVPView {
    void updatePOSStoreBean(POSStoreBean posStoreBean);
    void updateTotalPice(String price);
    void updateBalanceTotalPice(String price);
    void getNowbalancePrice(String nowPrice);
    void updateAddress(AddressBean address);
    void getAdressFailure(String msg);
    void orderPlaceSuccess();
    void orderPlaceFailure(String msg);
    void getDataFailure(String msg);
    void showTitleSuccess(String title);//名称
    void showMoneySuccess(String money);//押金
    void showYunSuccess(String yun);//运费
    void showNumSuccess(String num);//购买数量
    void showGznumSuccess(String gz_num);//关注数量
    void showMiaoshuSuccess(String miaoshu);//描述
    void showImgSuccess(String img);//图片
    void onPwdCorrect();//密码正确
    void onPwdErro(String msg);//交易密码错误
    void showDialog();
    void dismissDialog();
}
