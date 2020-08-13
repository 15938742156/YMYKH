package com.ykh.yinmeng.ymykh2.activity.shouyixiangqing;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;

import java.util.List;

/**
 * 2018/11/10 15:05
 * Description：
 */
public class ShouyixiangqingPresenterImp<V extends ShouyixiangqingMVPView> extends BasePresenter<V> implements ShouyixiangqingPresenter<V> {

    public ShouyixiangqingPresenterImp() {
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
//        ServerApi.<ShouyixiangqingResponse>getLog("1")
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) {
//                        Log.d("udong", "获取收益详情");
//                        getAttachedView().showDialog();
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ShouyixiangqingResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        getCompositeDisposable().add(d);
//                    }
//
//                    @Override
//                    public void onNext(ShouyixiangqingResponse response) {
//                        if (response.getCode() == 1) {
//                            getAttachedView().getDataSuccess();
//                            ShouyixiangqingResponse.ShouyixiangqingPageBean shouyixiangqingPageBean = response.getData();
//                            List<ShouyixiangqingResponse.ShouyixiangqingPageBean.ShouyiBean> beanList = shouyixiangqingPageBean.getData();
//                        } else {
//                            getAttachedView().getDataFailure(response.getMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        getAttachedView().getDataFailure(e.getLocalizedMessage());
//                        getAttachedView().dismissDialog();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        getAttachedView().dismissDialog();
//                    }
//                });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
