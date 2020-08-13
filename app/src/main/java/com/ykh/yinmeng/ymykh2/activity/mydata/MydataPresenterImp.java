package com.ykh.yinmeng.ymykh2.activity.mydata;

import android.util.Log;


import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.MydataResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MydataPresenterImp<V extends MydataMVPView> extends BasePresenter<V> implements MydataPresenter<V> {

    public MydataPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);

        ServerApi.<MydataResponse>getcenter().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) {
                Log.d("udong", "获取用户个人数据");
                getAttachedView().showDialog();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MydataResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                getCompositeDisposable().add(d);
            }

            @Override
            public void onNext(MydataResponse response) {
                if (response.getCode() == 1) {
                    String phone = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_PHONE);
                    getAttachedView().showTelSuccess(phone);
                    MydataResponse.DataBean.ExtendBean extendBean = response.getData().getExtend();
                    MydataResponse.DataBean.UserInfoBean userInfoBean = response.getData().getUserInfo();
                    if (extendBean != null) {
                        getAttachedView().showNameSuccess(userInfoBean.getName());
                        getAttachedView().showIdNumber(extendBean.getIdNumber());
                        getAttachedView().showSexSuccess(extendBean.getSex());
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
