package com.ykh.yinmeng.ymykh2.activity.xinyongka;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.xinyongkashenqing.XinyongkashenqingActivity;
import com.ykh.yinmeng.ymykh2.activity.xinyongkaxiangqing.XinyongkaxiangqingActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.NewXinyongkaResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkaResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XinyongkaPresenterImp<V extends XinyongkaMVPView> extends BasePresenter<V> implements XinyongkaPresenter<V> {

    private List<XinyongkaResponse.DataBean> xinyongkalist = new ArrayList<>();
    private List<NewXinyongkaResponse.DataBean.ListBean> cardlist = new ArrayList<>();
    private boolean selectshop = false;
    int pagee =1;
    int totalPage = 1;
    public XinyongkaPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }
//
//    @Override
//    public void refreshData() {
//        ServerApi.<XinyongkaResponse>xinyongka()
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) {
//                        Log.d("udong", "信用卡列表");
//                        getAttachedView().showDialog();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<XinyongkaResponse>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        getCompositeDisposable().add(d);
//                    }
//
//                    @Override
//                    public void onNext(XinyongkaResponse response) {
//                        if (response.getCode() == 1) {
//                            List<XinyongkaResponse.DataBean> list = response.getData();
//                            if (list != null){
//                                xinyongkalist.clear();
//                                xinyongkalist.addAll(list);
//                                getAttachedView().getListSuccess(xinyongkalist);
//                            }
//                        } else {
//                            getAttachedView().getListFailure(response.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        getAttachedView().getListFailure(e.getLocalizedMessage());
//                        getAttachedView().dismissDialog();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        getAttachedView().dismissDialog();
//                    }
//                });
//    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onToggleItem(Activity activity,int position) {
        Intent intent = new Intent(App.getContext(), XinyongkaxiangqingActivity.class);
        intent.putExtra("product_id",cardlist.get(position).getId());//产品ID
        intent.putExtra("bank_id",cardlist.get(position).getCooperative_bacooperative_bank_id());//机构ID
        intent.putExtra("expect_amount",cardlist.get(position).getMax_amount());//期望金额
        intent.putExtra("amount",cardlist.get(position).getCo_amount());//佣金

        intent.putExtra("name",cardlist.get(position).getName());//信用卡名称
        intent.putExtra("annual_fee",cardlist.get(position).getAnnual_fee());//年费说明
        intent.putExtra("abstractx",cardlist.get(position).getAbstractX());//简介
        intent.putExtra("characteristic",cardlist.get(position).getCharacteristic());//卡种特色
        intent.putExtra("raiders",cardlist.get(position).getRaiders());//办卡攻略
        intent.putExtra("je_amount",cardlist.get(position).getJe_amount());//平均开卡金额
        intent.putExtra("max_amount",cardlist.get(position).getMax_amount());//最大开卡金额
        intent.putExtra("tag",cardlist.get(position).getTag());//特色标签
        intent.putExtra("feedback_loop",cardlist.get(position).getFeedback_loop());//结算周期
        intent.putExtra("banner",cardlist.get(position).getLogo_img());//banner

        activity.startActivityForResult(intent, 170);
    }

    @Override
    public void Data(int page) {
        ServerApi.<NewXinyongkaResponse>newxinyongka(String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "信用卡列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewXinyongkaResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(NewXinyongkaResponse response) {
                        if (response.getCode() == 200) {
                            totalPage = response.getData().getPage().getTotalPage();
                            getAttachedView().getTotalpage(totalPage);
                            List<NewXinyongkaResponse.DataBean.ListBean> list = response.getData().getList();
                            if (list != null){
                                cardlist.clear();
                                cardlist.addAll(list);
                                getAttachedView().getListSuccess(cardlist);
                            }
                        } else {
                            getAttachedView().getListFailure(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getListFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

}
