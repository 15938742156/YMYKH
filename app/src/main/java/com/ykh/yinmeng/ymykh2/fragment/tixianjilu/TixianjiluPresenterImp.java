package com.ykh.yinmeng.ymykh2.fragment.tixianjilu;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.TixianjiluResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TixianjiluPresenterImp<V extends TixianjiluMVPView> extends BasePresenter<V> implements TixianjiluPresenter<V> {

    public TixianjiluPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<TixianjiluResponse>drawmoneyjilu()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d("xinhui", "提现记录");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TixianjiluResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(TixianjiluResponse tixianjiluResponse) {
                        if (tixianjiluResponse.getCode() == 1){
                            List<TixianjiluResponse.DataBean> lowuserlist = tixianjiluResponse.getData();
                            if (lowuserlist != null){
                                getAttachedView().getListSuccess(lowuserlist);
                            }
                        }else {
                            getAttachedView().getListFailure(tixianjiluResponse.getMsg());
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
