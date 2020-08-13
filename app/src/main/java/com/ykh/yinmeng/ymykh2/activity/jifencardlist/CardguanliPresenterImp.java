package com.ykh.yinmeng.ymykh2.activity.jifencardlist;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.jifenaddcard.AddcardActivity;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.model.CardResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CardguanliPresenterImp <V extends CardguanliMVPView> extends BasePresenter<V> implements CardguanliPresenter<V> {

    private static final int REQUEST_CODE_CARD = 169;
    private static final int REQUEST_CODE_EDIT_CARD = 170;
    boolean selectCard = false;
    private List<CardBean> cardList = new ArrayList<>();
    public CardguanliPresenterImp(){
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
        Intent intent = activity.getIntent();
        selectCard = intent.getBooleanExtra("selectCard", false);
        getData();
    }

    @Override
    public void onToggleTianjiaBtn(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), AddcardActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_CARD);
    }

    @Override
    public void onToggleDeleteBtn(final int position) {
        ServerApi.<LzyResponse>bankcarddel(String.valueOf(cardList.get(position).getId()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "删除银行卡");
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
                            getAttachedView().delCardSuccess();
                            cardList.remove(position);
                            getAttachedView().onItemRemoved(position);
                        } else {
                            getAttachedView().delCardFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().delCardFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onToggleEditBtn(Activity activity, int position) {
        Intent intent = new Intent(activity.getApplicationContext(), AddcardActivity.class);
        intent.putExtra("CardBean", cardList.get(position));
        activity.startActivityForResult(intent, REQUEST_CODE_EDIT_CARD);
    }

    @Override
    public void onToggleItem(Activity activity, int position) {
        Intent intent;
        if (!selectCard) {
            intent = new Intent(activity.getApplicationContext(), com.ykh.yinmeng.ymykh2.activity.jifenaddcard.AddcardActivity.class);
            intent.putExtra("CardBean", cardList.get(position));
            activity.startActivityForResult(intent, REQUEST_CODE_EDIT_CARD);
        } else {
            intent = new Intent();
            intent.putExtra("CardBean", cardList.get(position));
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CARD) {
            //更新地址成功，更新界面
            getData();
        }else if (requestCode == REQUEST_CODE_EDIT_CARD){
            getData();
        }
    }

    private void getData() {
        ServerApi.<CardResponse>bankcardList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "获取银行卡列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(CardResponse cardResponse) {
                        if (cardResponse.getCode() == 1) {
                            List<CardBean> list = cardResponse.getData();
                            if (list != null){
                                cardList.clear();
                                cardList.addAll(list);
                                getAttachedView().getListSuccess(cardList);
                            }
                        } else {
                            getAttachedView().getListFailure(cardResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getListFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });

    }
}
