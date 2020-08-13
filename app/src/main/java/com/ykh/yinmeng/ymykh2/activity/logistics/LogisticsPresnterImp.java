package com.ykh.yinmeng.ymykh2.activity.logistics;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.LogisticsResponse;
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

public class LogisticsPresnterImp<V extends LogisticsMVPView> extends BasePresenter<V> implements LogisticsPresenter<V> {

    private MyOrderResponse.DataBeanX.OrderBean orderBean;

    private List<LogisticsResponse.TracesBean> list = new ArrayList<>();
    String ShipperCode;
    String LogisticCode;


    public LogisticsPresnterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onInit(Activity activity) {
        orderBean = activity.getIntent().getParcelableExtra("orderBean");

        Log.d("物流",String.valueOf(orderBean.getExp())+String.valueOf(orderBean.getExp_num())+orderBean.getPay_money()+orderBean.getRece_shr());
        getAttachedView().getLogoSuccess("http://wx.ykh9.com"+orderBean.getImg_feng());
        getAttachedView().getNameSuccess(orderBean.getTitle());
        getAttachedView().getNumSuccess(String.valueOf(orderBean.getNum()));
        getAttachedView().getpriceSuccess(String.valueOf(orderBean.getPrice()));
        getAttachedView().showYunSucess(String.valueOf(orderBean.getExp_fee()));
        getAttachedView().getWaybill(orderBean.getExp_num());
        getAttachedView().gettotalmoneySuccess(orderBean.getOrder_money());
        getAttachedView().getAddressSuccess(orderBean.getRece_shr());
        ShipperCode = orderBean.getExp();
        LogisticCode = orderBean.getExp_num();
        Log.d("物流编号",ShipperCode+LogisticCode);
        getData();
    }

    public void getData(){
        ServerApi.<LogisticsResponse>logistic(ShipperCode,LogisticCode)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "查看物流信息");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LogisticsResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LogisticsResponse response) {
                        if (TextUtils.equals("3",response.getState())) {
                            getAttachedView().showType("已签收");
                        } else {
                            getAttachedView().showType("待签收");
                        }
                        List<LogisticsResponse.TracesBean> tracesList = response.getTraces();
                        if (tracesList != null){
                            list.clear();
                            list.addAll(tracesList);
                            getAttachedView().getWuliuScccess(list);
                        }else {
                            getAttachedView().getWuliuFailure(response.getReason());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getDataFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }






}
