package com.ykh.yinmeng.ymykh2.activity.jihuomingxi;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.JihuomingxiResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JihuomingxiPresenterImp<V extends JihuomingxiMVPView> extends BasePresenter<V> implements JihuomingxiPresenter<V> {

    private List<JihuomingxiResponse.DataBean> jihuolist = new ArrayList<>();

    public JihuomingxiPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        getData();

    }
    public void getData(){
        ServerApi.<JihuomingxiResponse>activeLog()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "激活明细");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JihuomingxiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(JihuomingxiResponse response) {
                        if (response.getCode() == 1) {
                            List<JihuomingxiResponse.DataBean> list = response.getData();
                            if (list != null){
                                jihuolist.clear();
                                jihuolist.addAll(list);
                                getAttachedView().getListSuccess(jihuolist);
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

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
