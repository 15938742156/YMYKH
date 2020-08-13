package com.ykh.yinmeng.ymykh2.activity.jifenguanli;

import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.IntegralcenterResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JifenguanliPresenterImp<V extends JifenguanliMVPView> extends BasePresenter<V> implements JifenguanliPresenter<V> {


    public JifenguanliPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        getData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getData(){
        ServerApi.<IntegralcenterResponse>integralcenter()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "积分个人中心");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IntegralcenterResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(IntegralcenterResponse response) {
                        if (response.getCode() == 1) {
                            if (response.getData() != null){
                                IntegralcenterResponse.DataBean dataBean = response.getData();
                                getAttachedView().showCoinSuccess(dataBean.getCoin());
                                getAttachedView().showInCoinSuccess(dataBean.getInCoin());
                                getAttachedView().showOutCoinSuccess(dataBean.getOutCoin());
                                getAttachedView().showInRankSuccess(dataBean.getInRank());
                                getAttachedView().getStatusSuccess(dataBean.getLevel());
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_LEVEL, dataBean.getLevel());
                            }else {
                                Toast.makeText(App.getContext(),"请前去实名认证",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            getAttachedView().getDataFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });

    }


}
