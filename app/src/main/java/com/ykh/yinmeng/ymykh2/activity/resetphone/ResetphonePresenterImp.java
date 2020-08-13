package com.ykh.yinmeng.ymykh2.activity.resetphone;

import android.os.CountDownTimer;
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

/**
 * 2018/11/12 9:15
 * Description：
 */
public class ResetphonePresenterImp<V extends ResetphoneMVPView> extends BasePresenter<V> implements ResetphonePresenter<V> {

    private TimeCount time;

    public ResetphonePresenterImp() {
        super();
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        time.cancel();
    }

    @Override
    public void onToggleModifyBtn(final String tel, String yzm) {
        ServerApi.<LzyResponse>resetphone(tel, yzm)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "修改手机号");
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
                            getAttachedView().modifySuccess();
                            SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_PHONE,tel);
                        } else {
                            getAttachedView().modifyFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().modifyFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onToggleCodeBtn(String phone) {
        ServerApi.<LzyResponse>getYzm(phone, "5")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "修改手机号获取验证码");
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
                            getAttachedView().getYzmSuccess();
                            time.start();
                        } else {
                            getAttachedView().getYzmFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getYzmFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    class TimeCount extends CountDownTimer {

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // 计时过程
            getAttachedView().onTick(millisUntilFinished);
        }

        @Override
        public void onFinish() {
            // 计时完毕
            getAttachedView().onTickFinish();
        }
    }
}
