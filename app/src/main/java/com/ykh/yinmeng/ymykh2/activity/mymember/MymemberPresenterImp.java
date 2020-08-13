package com.ykh.yinmeng.ymykh2.activity.mymember;

import android.util.Log;
import android.view.View;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.MyhuiyuanResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.List;

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
public class MymemberPresenterImp<V extends MymemberMVPView> extends BasePresenter<V> implements MymemberPresenter<V> {

    private List<MyhuiyuanResponse.DataBean.LowInfoBean> lowinfoList = new ArrayList<>();
    private MyhuiyuanResponse.DataBean.InfoBean infoBean;
    public MymemberPresenterImp() {
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

    @Override
    public void onToggleSpinnerBtn(View view) {
        if (infoBean != null) {
            getAttachedView().showPopupMenu(infoBean, view);
        }
    }

    @Override
    public void getData() {
        ServerApi.<MyhuiyuanResponse>myhuiyuan()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取我的会员数据");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyhuiyuanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(MyhuiyuanResponse response) {
                        if (response.getCode() == 1) {
                            List<MyhuiyuanResponse.DataBean.LowInfoBean> list = response.getData().getLowInfo();
                            infoBean = response.getData().getInfo();
                            getAttachedView().showTotalSumSuccess(infoBean);
                            if (list != null) {
                                lowinfoList.clear();
                                lowinfoList.addAll(list);
                                getAttachedView().getDataSuccess(lowinfoList);
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
