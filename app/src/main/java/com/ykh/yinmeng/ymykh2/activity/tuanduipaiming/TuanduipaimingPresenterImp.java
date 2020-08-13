package com.ykh.yinmeng.ymykh2.activity.tuanduipaiming;

import android.util.Log;


import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.TuanduipaimingResponse;
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
public class TuanduipaimingPresenterImp<V extends TuanduipaimingMVPView> extends BasePresenter<V> implements TuanduipaimingPresenter<V> {
    private List<TuanduipaimingResponse.PaimingBean> paiminglist = new ArrayList<>();
    public TuanduipaimingPresenterImp() {
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

    @Override
    public void getData() {
        ServerApi.<TuanduipaimingResponse>teamranking()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取团队排名信息");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TuanduipaimingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(TuanduipaimingResponse response) {
                        if (response.getCode() == 1) {List<TuanduipaimingResponse.PaimingBean> list = response.getData();
                            if (list != null) {
                                paiminglist.clear();
                                paiminglist.addAll(list);
                                getAttachedView().getDataSuccess(paiminglist);
                            }
                        } else {
                            getAttachedView().getDataFailure(response.getMsg());
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
