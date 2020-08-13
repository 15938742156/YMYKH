package com.ykh.yinmeng.ymykh2.activity.shanghuchaxun;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.ShanghuchaxunResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShanghuchaxunPresenterImp<V extends ShanghuchaxunMVPView> extends BasePresenter<V> implements ShanghuchaxunPresenter<V> {

    private List<ShanghuchaxunResponse.DataBean> chaxunlist = new ArrayList<>();
    private int type = 1;
    Context context;

    public ShanghuchaxunPresenterImp(){
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
    public void setType(int type) {
        //1.SN查询; 2.手机号查询 3.姓名查询
        this.type = type;
    }

    @Override
    public void onSousuoBtn(String keyword) {
        ServerApi.<ShanghuchaxunResponse>business_query(String.valueOf(type),keyword)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "模糊搜索全部机具");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShanghuchaxunResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(ShanghuchaxunResponse response) {
                        if (response.getCode() == 1) {
                            List<ShanghuchaxunResponse.DataBean> list =response.getData();
                            if (list != null){
                                chaxunlist.clear();
                                chaxunlist.addAll(list);
                                getAttachedView().getListSuccess(chaxunlist);
                            }
                        } else {
                            getAttachedView().getDataFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getDataFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }
}
