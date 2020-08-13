package com.ykh.yinmeng.ymykh2.fragment.cashfragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.RebateResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CashFragmentPresenterImp<V extends CashFragmentMVPView> extends BasePresenter<V> implements CashFragmentPresenter<V> {

    private static final int REQUEST_CODE_CARD = 169;
    private static final int REQUEST_CODE_EDIT_CARD = 170;
    private List<RebateResponse.DataBean> rebatelist = new ArrayList<>();
    public CashFragmentPresenterImp(){
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
        getData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CARD) {
            //更新记录成功，更新界面

            getData();
        }
    }

    public void getData() {
        ServerApi.<RebateResponse>cash()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "押金退还");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RebateResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(RebateResponse balanceResponse) {
                        if (balanceResponse.getCode() == 1) {
                            List<RebateResponse.DataBean> list = balanceResponse.getData();
                            if (list != null){
                                rebatelist.clear();
                                rebatelist.addAll(list);
                                getAttachedView().getListSuccess(rebatelist);
                            }
                        } else {
                            getAttachedView().getListFailure(balanceResponse.getMsg());
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
