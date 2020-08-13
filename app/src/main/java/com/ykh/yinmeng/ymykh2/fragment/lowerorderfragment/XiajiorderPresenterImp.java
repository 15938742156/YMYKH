package com.ykh.yinmeng.ymykh2.fragment.lowerorderfragment;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.XiajiorderResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XiajiorderPresenterImp<V extends XiajiorderMVPView> extends BasePresenter<V> implements XiajiorderPresenter<V> {
    private List<XiajiorderResponse.DataBeanX.DataBean> lowerorderlist = new ArrayList<>();


    public XiajiorderPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<XiajiorderResponse>lowerList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取订单中的全部订单");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<XiajiorderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(XiajiorderResponse response) {
                        if (response.getCode() == 1) {
                            List<XiajiorderResponse.DataBeanX.DataBean> beanList = response.getData().getData();
                            if (beanList != null) {
                                lowerorderlist.clear();
                                lowerorderlist.addAll(beanList);

                                getAttachedView().getDataSuccess(lowerorderlist);
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

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
