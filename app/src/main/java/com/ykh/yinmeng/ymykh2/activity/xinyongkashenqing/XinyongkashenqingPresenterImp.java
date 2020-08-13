package com.ykh.yinmeng.ymykh2.activity.xinyongkashenqing;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.XinyongkashenqingResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XinyongkashenqingPresenterImp<V extends XinyongkashenqingMVPView> extends BasePresenter<V> implements XinyongkashenqingPresenter<V> {


    String product_id = null;
    String bank_id = null;
    String expect_amount = null;
    String amount = null;

    public XinyongkashenqingPresenterImp(){
        super();
    }

    @Override
    public void onInit(Activity activity) {
        Intent intent = activity.getIntent();
        product_id = String.valueOf(intent.getExtras().getString("product_id"));
        bank_id = String.valueOf(intent.getExtras().getString("bank_id"));
        expect_amount = String.valueOf(intent.getExtras().getString("expect_amount"));
        amount = String.valueOf(intent.getExtras().getString("amount"));
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<XinyongkashenqingResponse>xinyongkaorder(product_id,bank_id,expect_amount,amount)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "信用卡申请接口调用");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<XinyongkashenqingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(XinyongkashenqingResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getUrlSuccess(response.getData());

                            Log.v("ddddddddd",response.getData());
                        } else {
                            getAttachedView().getUrlFailure(response.getMsg());
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

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
