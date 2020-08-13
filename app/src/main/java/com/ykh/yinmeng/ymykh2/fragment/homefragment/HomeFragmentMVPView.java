package com.ykh.yinmeng.ymykh2.fragment.homefragment;


import com.ykh.yinmeng.ymykh2.activity.base.IMVPView;
import com.ykh.yinmeng.ymykh2.bean.HomegoodsBean;
import com.ykh.yinmeng.ymykh2.bean.HomelogoBean;

import java.util.List;

public interface HomeFragmentMVPView extends IMVPView {

    void getLogoListSuccess(List<HomelogoBean> logoList);
    void showGoods(HomegoodsBean goods);
    void showNotice(List<String> notices);
    void showRoll(List<String> rolls);
    void showMoney(String money);
    void showUser(String user);
    void showShop(String shop);
    void getListFailure(String msg);
    void onNoticeToggled(String url);
    void showDialog();
    void dismissDialog();
}
