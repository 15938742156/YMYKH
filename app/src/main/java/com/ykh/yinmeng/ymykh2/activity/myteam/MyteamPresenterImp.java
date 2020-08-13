package com.ykh.yinmeng.ymykh2.activity.myteam;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.MyTeamResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

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
public class MyteamPresenterImp<V extends MyteamMVPView> extends BasePresenter<V> implements MyteamPresenter<V> {

    public MyteamPresenterImp() {
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<MyTeamResponse>myteam()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("ykh", "我的团队");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyTeamResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(MyTeamResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getDataSuccess();


                            MyTeamResponse.DataBean data = response.getData();
                            getAttachedView().showSumDeal(data.getSumDeal());//我的直营 当日激活商户数
                            getAttachedView().showDayUser(String.valueOf(data.getDayUser()));//我的会员 当日新增会员数
                            getAttachedView().showZShop(String.valueOf(data.getZJqsumCount()));//我的会员 当日激活台数
                            getAttachedView().showZJqCount(String.valueOf(data.getShopsCount()));//我的直营 当前直营商户数
                            getAttachedView().showActiveCount(String.valueOf(data.getZJqCount()));//我的直营 当月直营总交易额
                            getAttachedView().showInfo(String.valueOf(data.getInfo()));//我的会员, 我的下级会员总人数
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
