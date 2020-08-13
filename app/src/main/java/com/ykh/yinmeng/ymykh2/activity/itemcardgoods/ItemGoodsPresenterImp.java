package com.ykh.yinmeng.ymykh2.activity.itemcardgoods;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.jifensubmit.SubmitActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.ItemcardResponse;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemGoodsPresenterImp<V extends ItemGoodsMVPView> extends BasePresenter<V> implements ItemGoodsPresenter<V> {

    private List<JifenItemgoodsResponse.DataBean> itemlist = new ArrayList<>();
    Intent intent;
    String id = null;
    String img = null;
    int typee;
    private boolean goods = false;

    public ItemGoodsPresenterImp(){
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
        intent = activity.getIntent();
        id = String.valueOf(intent.getExtras().getInt("id"));
        typee = Integer.valueOf(intent.getExtras().getInt("typee"));
        img = String.valueOf(intent.getExtras().getString("img"));
        getAttachedView().showImgSuccess(img);
        Log.v("ididid",id+img);
        goods = intent.getBooleanExtra("goods", false);
        getData();
    }

    @Override
    public void onExchangeBt(Activity activity) {
        intent = new Intent(activity, SubmitActivity.class);
        intent.putExtra("goods",true);
        intent.putExtra("typee",typee);
        intent.putExtra("id",id);
        activity.startActivity(intent);
    }

    public void getData(){
        ServerApi.<JifenItemgoodsResponse>goodslist(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "分类商品列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JifenItemgoodsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(JifenItemgoodsResponse response) {
                        if (response.getCode() == 1) {
                            List<JifenItemgoodsResponse.DataBean> list = response.getData();
                            if (list != null){
                                itemlist.clear();
                                itemlist.addAll(list);
                                getAttachedView().getListSuccess(itemlist);
                            }
                        } else {
                            getAttachedView().getListFailure(response.getMsg());
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
