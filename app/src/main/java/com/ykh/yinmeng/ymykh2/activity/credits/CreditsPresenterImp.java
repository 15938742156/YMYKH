package com.ykh.yinmeng.ymykh2.activity.credits;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.addressadd.AddaddressActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.itemcard.ItemCardActivity;
import com.ykh.yinmeng.ymykh2.activity.jifenguanli.JifenguanliActivity;
import com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing.ShanghuzicaixiangqingActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CreditsPresenterImp <V extends CreditsMVPView> extends BasePresenter<V> implements CreditsPresenter<V> {

    private List<CreditsResponse.DataBean> creditslist = new ArrayList<>();

    public CreditsPresenterImp(){
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
        ServerApi.<CreditsResponse>creditsList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "积分兑换列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CreditsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(CreditsResponse response) {
                        if (response.getCode() == 1) {
                            List<CreditsResponse.DataBean> list = response.getData();
                            if (list != null){
                                creditslist.clear();
                                creditslist.addAll(list);
                                getAttachedView().getListSuccess(creditslist);
                            }
                        } else {
                            getAttachedView().getListFailure(response.getMsg());
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

    @Override
    public void onToggleItem(Activity activity, int position) {
        Intent intent = new Intent(App.getContext(), ItemCardActivity.class);
        intent.putExtra("id",creditslist.get(position).getId());
        intent.putExtra("title",creditslist.get(position).getTitle());
        intent.putExtra("img",creditslist.get(position).getLogo());
        intent.putExtra("ratio",creditslist.get(position).getRatio());
        activity.startActivityForResult(intent, 170);

    }

    @Override
    public void onToggleTianjiaBtn(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), JifenguanliActivity.class);
        activity.startActivityForResult(intent, 169);
    }
}
