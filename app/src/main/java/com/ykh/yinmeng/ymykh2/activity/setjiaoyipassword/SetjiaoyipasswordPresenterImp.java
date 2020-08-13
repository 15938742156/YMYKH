package com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.accard.ACCardActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
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
public class SetjiaoyipasswordPresenterImp<V extends SetjiaoyipasswordMVPView> extends BasePresenter<V> implements SetjiaoyipasswordPresenter<V> {

    private TimeCount time;
    public SetjiaoyipasswordPresenterImp() {
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
    public void onToggleQuedingBtn(final Activity activity, String username, String code, String pwd) {
        ServerApi.<LzyResponse>setPayPwd(username, code, pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "设置交易密码");
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
                            Intent preIntent = activity.getIntent();
                            IDCardBean resultZM = preIntent.getParcelableExtra("resultZM");

                            Intent intent = new Intent(App.getContext(), ACCardActivity.class);
                            intent.putExtra("resultZM", resultZM);
                            activity.startActivity(intent);
                            activity.finish();

                            getAttachedView().setjiaoyipasswordSuccess();
                        } else {
                            getAttachedView().setjiaoyipasswordFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().setjiaoyipasswordFailure(e.getLocalizedMessage());
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
        ServerApi.<LzyResponse>getYzm(phone, "6")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "设置交易密码获取验证码");
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
