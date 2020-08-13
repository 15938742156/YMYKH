package com.ykh.yinmeng.ymykh2.fragment.myorderfragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.logistics.LogisticsActivity;
import com.ykh.yinmeng.ymykh2.activity.zhifufangshi.ZhifufangshiActivity;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.MyOrderResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 2018/11/10 15:05
 * Description：
 */
public class MyorderPresenterImp<V extends MyorderMVPView> extends BasePresenter<V> implements MyorderPresenter<V> {

    private static final int REQUEST_CODE_PAY = 143;
    private List<MyOrderResponse.DataBeanX.OrderBean> orderlist = new ArrayList<>();
    private int type = -1;

    public MyorderPresenterImp() {
        super();
    }

    @Override
    public void setType(int type) {
        //-1:全部订单，0：待付款，1：待发货，2：待收货
        this.type = type;
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<MyOrderResponse>orderList("")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取订单中的全部订单");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyOrderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(MyOrderResponse response) {
                        if (response.getCode() == 1) {
                            MyOrderResponse.DataBeanX orderBean = response.getData();
                            List<MyOrderResponse.DataBeanX.OrderBean> beanList = orderBean.getDataX();
                            if (beanList != null) {
                                orderlist.clear();
                                if (type == -1) {
                                    orderlist.addAll(beanList);
                                } else {
                                    for (MyOrderResponse.DataBeanX.OrderBean order : beanList) {
                                        if (order.status == type) {
                                            orderlist.add(order);
                                        }
                                    }
                                }
                                getAttachedView().getDataSuccess(orderlist);
                            }
                        } else {
                            getAttachedView().showFailureMsg(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showFailureMsg(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onToggleItem(Activity activity, View view) {
        int position = (int) view.getTag();
        MyOrderResponse.DataBeanX.OrderBean orderBean = orderlist.get(position);
        Log.d("我的订单",String.valueOf(orderBean.getExp())+String.valueOf(orderBean.getExp_num())+orderBean.getPay_money()+orderBean.getRece_shr());
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_quxiao:
                switch (orderBean.getStatus()) {
                    case 0://取消订单
                        orderdel(position, String.valueOf(orderBean.getOut_trade_no()));
                        break;
                    case 2://查看物流
                        intent = new Intent(activity, LogisticsActivity.class);
                        intent.putExtra("orderBean", orderBean);
                        activity.startActivity(intent);
                        break;
                }
                break;
            case R.id.bt_fukuan:
                switch (orderBean.status) {
                    case 0://立即付款
                        intent = new Intent(activity, ZhifufangshiActivity.class);
                        intent.putExtra("out_trade_no", orderBean.getOut_trade_no());
                        intent.putExtra("pay_money", orderBean.getPay_money());
                        activity.startActivityForResult(intent,REQUEST_CODE_PAY);
                        break;
                    case 1://提醒卖家
                        getAttachedView().showTixing("提醒成功");
                        break;
                    case 2://确认收货
                        orderConfirm(orderBean.getOut_trade_no());
                        break;
                    case 3://查看物流
                        intent = new Intent(activity, LogisticsActivity.class);
                        intent.putExtra("orderBean", orderBean);
                        activity.startActivity(intent);
                        break;
                }
                break;
        }
    }

    private void orderdel(final int position, String id) {
        ServerApi.<LzyResponse>orderdel(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "取消订单");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            Log.e("udong", "取消订单成功");
                            orderlist.remove(position);
                            getAttachedView().notifyItemRemoved(position);
                        } else {
                            getAttachedView().showFailureMsg(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showFailureMsg(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    private void orderConfirm(String out_trade_no) {
        ServerApi.<LzyResponse>orderConfirm(out_trade_no)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "确认订单");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            Log.e("udong", "确认订单成功");
                        } else {
                            getAttachedView().showFailureMsg(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showFailureMsg(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }
}
