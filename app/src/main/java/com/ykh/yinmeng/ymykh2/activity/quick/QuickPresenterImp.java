package com.ykh.yinmeng.ymykh2.activity.quick;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.setLoginpassword.SetLoginpasswordActivity;
import com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword.SetjiaoyipasswordPresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.common.jpush.JpushClient;
import com.ykh.yinmeng.ymykh2.model.LoginResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.Convert;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

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
public class QuickPresenterImp<V extends QuickMVPView> extends BasePresenter<V> implements QuickPresenter<V> {

    private TimeCount time;
    public QuickPresenterImp() {
        super();
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        String token = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_TOKEN);
        if (!token.equals("")) {
            getAttachedView().autoLogin();
        }
        JpushClient.getInstance().init(App.getContext());
        JpushClient.getInstance().resumePush();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        time.cancel();
    }

    @Override
    public void onToggleLoginBtn(String username, String code) {
        SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_PHONE, username);
        ServerApi.<LzyResponse>qLogin(username, code)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("login", "短信登陆");
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
                            getAttachedView().loginSuccess();
                            try {
                                JSONObject data = new org.json.JSONObject(response.getData().toString());
                                String token = data.optString("token");
                                String level = data.optString("level");
                                String isReal = data.optString("isReal");
                                String aliAccount = data.optString("aliAccount");
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_TOKEN, token);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_LEVEL, level);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, isReal);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_ALIACCOUNT, aliAccount);
                                int user_id = data.optInt("user_id");
                                JpushClient.getInstance().setAlias(String.valueOf(user_id));
                            } catch (JSONException e) {
                                Log.d("ykh", e.getLocalizedMessage());
                            }
                        }
                        if(response.getCode() == 2){
                            try {
                                JSONObject data = new org.json.JSONObject(response.getData().toString());
                                String token = data.optString("token");
                                String level = data.optString("level");
                                String isReal = data.optString("isReal");
                                String aliAccount = data.optString("aliAccount");
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_TOKEN, token);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_LEVEL, level);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, isReal);
                                SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_ALIACCOUNT, aliAccount);
                                int user_id = data.optInt("user_id");
                                JpushClient.getInstance().setAlias(String.valueOf(user_id));
                            } catch (JSONException e) {
                                Log.d("ykh", e.getLocalizedMessage());
                            }
                            Intent intent = new Intent(App.getContext(), SetLoginpasswordActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            App.getContext().startActivity(intent);
                        } else {
                            getAttachedView().loginFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().loginFailure(e.getLocalizedMessage());
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
        ServerApi.<LzyResponse>getYzm(phone, "1")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("ykh", "短信登陆获取验证码");
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
