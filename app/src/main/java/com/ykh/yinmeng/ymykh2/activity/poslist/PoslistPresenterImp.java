package com.ykh.yinmeng.ymykh2.activity.poslist;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.shanghuzicaixiangqing.ShanghuzicaixiangqingActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.ShanghuzicaiResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PoslistPresenterImp<V extends PoslistMVPView> extends BasePresenter<V> implements PoslistPresenter<V> {

    private List<ShanghuzicaiResponse.DataBeanX.DataBean> poslist = new ArrayList<>();
    private boolean selectshop = false;

    public PoslistPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }

    @Override
    public void refreshData() {
        ServerApi.<ShanghuzicaiResponse>shanghuzicai()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "pos机列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShanghuzicaiResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(ShanghuzicaiResponse response) {
                        if (response.getCode() == 1) {
                            List<ShanghuzicaiResponse.DataBeanX.DataBean> list = response.getData().getData();
                            if (list != null){
                                poslist.clear();
                                poslist.addAll(list);
                                getAttachedView().getListSuccess(poslist);
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onToggleItem(Activity activity,int position) {
        ServerApi.<LzyResponse>POSAttention(String.valueOf(poslist.get(position).getId()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "POS机关注度");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getAttentionSuccess();
                        } else {
                            getAttachedView().getAttentionFailuye(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getAttentionFailuye(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
                Intent intent = new Intent(App.getContext(), ShanghuzicaixiangqingActivity.class);
                intent.putExtra("id",poslist.get(position).getId());
                intent.putExtra("title",poslist.get(position).getTitle());
                intent.putExtra("img",poslist.get(position).getImg_feng());
                intent.putExtra("money",poslist.get(position).getPrice());
                intent.putExtra("miaoshu",poslist.get(position).getDescription());
                intent.putExtra("gz_num",poslist.get(position).getGz_num());
                intent.putExtra("yun",poslist.get(position).getYun());
                activity.startActivityForResult(intent, 170);
                Log.v("*******",poslist.get(position).getId()+"*"+poslist.get(position).getTitle()+"*"+poslist.get(position).getPrice()+"*"+poslist.get(position).getYun()+"*"+poslist.get(position).getImg_feng()+"*"+poslist.get(position).getTab()+"*"+poslist.get(position).getGz_num());
    }

}
