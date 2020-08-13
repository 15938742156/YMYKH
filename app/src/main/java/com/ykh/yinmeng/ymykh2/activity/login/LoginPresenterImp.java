package com.ykh.yinmeng.ymykh2.activity.login;

import android.util.Log;


import com.google.gson.reflect.TypeToken;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.common.jpush.JpushClient;
import com.ykh.yinmeng.ymykh2.model.LoginResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.Convert;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 2018/11/10 9:53
 * Description：
 */
public class LoginPresenterImp<V extends LoginMVPView> extends BasePresenter<V> implements LoginPresenter<V> {

    LoginResponse.DataBean dataBean;


    public LoginPresenterImp() {
        super();
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
    }

    @Override
    public void onToggleLoginBtn(String username,String passwd) {
        SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_PHONE, username);
        ServerApi.<LzyResponse>login(username, passwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("login", "登录请求");
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
                            Type type = new TypeToken<LoginResponse.DataBean>() {}.getType();
                            dataBean = Convert.fromJson(response.getData().toString(), type);
                            Log.d("Login1", "data:Token:"+dataBean.getToken());
                            Log.d("Login2", "data:User_id:"+dataBean.getUser_id());
                            Log.d("Login3", "data:isReal:"+dataBean.getIsReal());
                            Log.d("Login4", "data:level:"+dataBean.getJifenlevel());
                            Log.d("Login5", "data:AliAccount:"+dataBean.getAliAccount());
                            JpushClient.getInstance().setAlias(String.valueOf(dataBean.getUser_id()));
                            SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_TOKEN, dataBean.getToken());
                            SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, dataBean.getIsReal());
                            SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_LEVEL, dataBean.getJifenlevel());
                            SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_ALIACCOUNT, dataBean.getAliAccount());
                            getAttachedView().loginSuccess();
                        } else {
                            getAttachedView().loginFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getAttachedView().loginFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

}
