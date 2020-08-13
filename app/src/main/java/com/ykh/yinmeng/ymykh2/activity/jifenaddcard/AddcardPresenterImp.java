package com.ykh.yinmeng.ymykh2.activity.jifenaddcard;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddcardPresenterImp<V extends AddcardMVPView> extends BasePresenter<V> implements AddcardPresenter<V> {

    String id = null;
    Intent intent;
    CardBean cardBean;

    public AddcardPresenterImp(){
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
        intent = activity.getIntent();
        cardBean = intent.getParcelableExtra("CardBean");
        int status = -1;
        if (cardBean != null){
            getAttachedView().setBankName(cardBean.getBanks());
            getAttachedView().setBanksNumber(cardBean.getBanksNumber().replace(" ",""));
            getAttachedView().setBranch(cardBean.getBranch());
//            getAttachedView().setBankAccount(cardBean.getBanksAccount());
            status = cardBean.getStatus();
        }
        getAttachedView().setStatus(status == 1);
    }

    @Override
    public void onToggleAddBtn(String bankName, String banksNumber, String branch, String status, String banksAccount) {

        if (cardBean == null) {
            ServerApi.<LzyResponse>bankcardAdd(bankName, banksNumber.replace(" ",""), branch, status, banksAccount)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("xinhui", "新增银行卡");
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
                                getAttachedView().addBankcardSuccess();
                            } else {
                                getAttachedView().addBankcardFailure(response.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            getAttachedView().addBankcardFailure(e.getLocalizedMessage());
                            getAttachedView().dismissDialog();
                        }

                        @Override
                        public void onComplete() {
                            getAttachedView().dismissDialog();
                        }
                    });
        } else {
            ServerApi.<LzyResponse>bankcardEdit(bankName, banksNumber, branch, status, banksAccount,String.valueOf(cardBean.getId()))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("xinhui", "编辑银行卡");
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
                                getAttachedView().editBankcardSuccess();
                            } else {
                                getAttachedView().editBankcardFailure(response.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            getAttachedView().editBankcardFailure(e.getLocalizedMessage());
                            getAttachedView().dismissDialog();
                        }

                        @Override
                        public void onComplete() {
                            getAttachedView().dismissDialog();
                        }
                    });
        }

    }

    @Override
    public void onDelBtn(Activity activity) {

    }
}
