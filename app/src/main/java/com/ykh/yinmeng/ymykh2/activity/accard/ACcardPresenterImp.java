package com.ykh.yinmeng.ymykh2.activity.accard;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ACcardPresenterImp<V extends ACcardMVPView> extends BasePresenter<V> implements ACcardPresenter<V> {

    String id = null;
    Intent intent;
    CardBean cardBean;
    IDCardBean resultZM = new IDCardBean();
    String banksAccounts;


    public ACcardPresenterImp(){
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
        resultZM = intent.getParcelableExtra("IDCardBean");
        if (resultZM != null){
            banksAccounts = resultZM.getName();
        }
        int status = -1;
        if (cardBean != null){
            getAttachedView().setBankName(cardBean.getBanks());
            getAttachedView().setBanksNumber(cardBean.getBanksNumber().replace(" ",""));
//            getAttachedView().setBranch(cardBean.getId());
//            status = cardBean.getStatus();
        }
        getAttachedView().setStatus(status == 1);
    }

    @Override
    public void onToggleAddBtn(String banksAccount,String banksNumber,String bankName,String branch,String status) {
            ServerApi.<LzyResponse>bankcardAdd(banksAccounts,banksNumber.replace(" ",""),bankName, branch, "1")
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("udong", "银行卡认证");
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
        }
        }


