package com.ykh.yinmeng.ymykh2.activity.xinyongkajindu;

import android.app.Activity;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class XinyongkajinduPresenterImp<V extends XinyongkajinduMVPView> extends BasePresenter<V> implements XinyongkajinduPresenter<V> {


    String tel = null;

    public XinyongkajinduPresenterImp(){
        super();
    }

    @Override
    public void onInit(Activity activity) {
        tel = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<LzyResponse>xinyongkajindu(tel)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "信用卡申请进度");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getUrlSuccess(String.valueOf(response.getData()));

                            Log.v("ddddddddd",String.valueOf(response.getData()));
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
