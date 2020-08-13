package com.ykh.yinmeng.ymykh2.model;


import com.ykh.yinmeng.ymykh2.bean.HomebroadcastBean;
import com.ykh.yinmeng.ymykh2.bean.HomegoodsBean;
import com.ykh.yinmeng.ymykh2.bean.HomelogoBean;
import com.ykh.yinmeng.ymykh2.bean.HomerollBean;

import java.util.List;

public class HomeResponse {


    /**
     * user : 0
     * sum_money : 0
     * logo : [{"id":5,"logo":"http://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-12-11/5c0f128148624.png","cid":1}]
     * goods : [{"id":6,"goods_name":"优盛伙伴创业礼包","logo":"http://ldymapp.oss-cn-zhangjiakou.aliyuncs.com/goods/2018-12-04/5c06196d0ee8f.png","keywords":"最新产品全新上市"}]
     * shop : 0
     * broadcast : [{"id":2,"cid":4,"title":"优盛伙伴全新模式盛磅来袭！","keyword":"优盛伙伴全新模式盛磅来袭！","body":"优盛伙伴全新模式盛磅来袭！"}]
     * roll : "[{"id": 9,"tel": "15537189254","money": "5","addtime": "1516874735","edittime": "1516874735"}]
     */

    private int user;
    public String sum_money;
    private int shop;
    private List<HomelogoBean> logo;
    private List<HomegoodsBean> goods;
    private List<HomerollBean> roll;
    private List<HomebroadcastBean> broadcast;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getSum_money() {
        return sum_money;
    }

    public void setSum_money(String sum_money) {
        this.sum_money = sum_money;
    }

    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public List<HomelogoBean> getLogo() {
        return logo;
    }

    public void setLogo(List<HomelogoBean> logo) {
        this.logo = logo;
    }

    public List<HomegoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<HomegoodsBean> goods) {
        this.goods = goods;
    }

    public List<HomerollBean> getRoll() {
        return roll;
    }

    public void setRoll(List<HomerollBean> roll) {
        this.roll = roll;
    }

    public List<HomebroadcastBean> getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(List<HomebroadcastBean> broadcast) {
        this.broadcast = broadcast;
    }
}
