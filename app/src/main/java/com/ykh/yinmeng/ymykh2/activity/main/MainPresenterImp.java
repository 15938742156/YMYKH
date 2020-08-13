package com.ykh.yinmeng.ymykh2.activity.main;

import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.RedpacketResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.RedpacketPopup;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPresenterImp<V extends MainMVPView> extends BasePresenter<V> implements MainPresenter<V> {

    private int num = 0;
    private double money = 0;

    public MainPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        getData();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getData(){
        ServerApi.<RedpacketResponse>chairedpacket()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("ykh", "拆红包");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RedpacketResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(RedpacketResponse balanceResponse) {
                        if (balanceResponse.getCode() == 1) {
                            List<RedpacketResponse.DataBean> list = balanceResponse.getData();
                            if (list != null){
                                num = list.size();
                                for (int i = 0; i < list.size(); i++){
                                    money += Double.parseDouble(list.get(i).getMoney());

                                }
                                getAttachedView().showNumSuccess(num,String.valueOf(money));
                            }
                        } else {
                            getAttachedView().getDataFailure(balanceResponse.getMsg());
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
}
