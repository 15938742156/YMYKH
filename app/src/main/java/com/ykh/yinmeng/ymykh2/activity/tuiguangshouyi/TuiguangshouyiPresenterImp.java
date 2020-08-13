package com.ykh.yinmeng.ymykh2.activity.tuiguangshouyi;

import android.util.Log;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.TuiguangshouyiResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
public class TuiguangshouyiPresenterImp<V extends TuiguangshouyiMVPView> extends BasePresenter<V> implements TuiguangshouyiPresenter<V> {

    public TuiguangshouyiPresenterImp() {
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<TuiguangshouyiResponse>getMonths()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取推广收益信息");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TuiguangshouyiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(TuiguangshouyiResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getDataSuccess();
                            TuiguangshouyiResponse.DataBean data = response.getData();
                            List<TuiguangshouyiResponse.DataBean.ListBean> sumShare = data.getList();

                            SimpleDateFormat format = new SimpleDateFormat("yyyyMM", Locale.getDefault());
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(new Date());
                            Date date;
                            String[] xLabel = {"201806", "201807", "201808", "201809", "201810", "201811", "201812"};
                            String[] chartData = {"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"};
                            for (int i = 6; i >= 0; i--) {
                                date = calendar.getTime();
                                xLabel[i] = format.format(date);
                                if (sumShare != null && sumShare.size() > 0) {
                                    for (int j = 0; j < sumShare.size(); j++) {
                                        if (xLabel[i].equals(String.valueOf(sumShare.get(j).getTime().replaceAll("-","")))) {
                                            chartData[i] = sumShare.get(j).getMoney();
                                        }
                                    }
                                }
                                calendar.add(Calendar.MONTH, - 1);
                            }

                            getAttachedView().showChartView(xLabel,chartData);
                            getAttachedView().showActiveCount(String.valueOf(data.getMoney()));
                            getAttachedView().showActiveShop(String.valueOf(data.getShop()));
                            getAttachedView().showUserAccount(String.valueOf(data.getUser()));
                            getAttachedView().showZSumAccount(String.valueOf(data.getZSum()));
                            getAttachedView().showActiveZShop(String.valueOf(data.getZShop()));
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
