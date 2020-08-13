package com.ykh.yinmeng.ymykh2.activity.jifenshouzhi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.JifenShouzhiResponse;
import com.ykh.yinmeng.ymykh2.model.JifenjinduResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JifenshouzhiPresenterImp<V extends JifenshouzhiMVPView> extends BasePresenter<V> implements JifenshouzhiPresenter<V> {

    private List<JifenShouzhiResponse.DataBeanX.DataBean> itemlist = new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    private Calendar calendar = Calendar.getInstance();
    String stime;
    String etime;

    public JifenshouzhiPresenterImp(){
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
    public void onsTimeBt(Activity activity) {
        datePickerDialog = new DatePickerDialog(
                activity,AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                stime = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth);
                getAttachedView().showsTimeSuccess(stime);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.TITLE_CHANGED);
    }

    @Override
    public void oneTimeBt(Activity activity) {
        datePickerDialog = new DatePickerDialog(
                activity,AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //monthOfYear 得到的月份会减1所以我们要加1
                etime = String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + Integer.toString(dayOfMonth);
                getAttachedView().showeTimeSuccess(etime);
            }
        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        //自动弹出键盘问题解决
        datePickerDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.TITLE_CHANGED);
    }

    @Override
    public void onConfirm(String startDate, String endDate) {
        ServerApi.<JifenShouzhiResponse>shouzhi(startDate,endDate)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "积分收支明细");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JifenShouzhiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(JifenShouzhiResponse response) {
                        if (response.getCode() == 1) {
                            List<JifenShouzhiResponse.DataBeanX.DataBean> list = response.getData().getData();
                            if (list != null){
                                itemlist.clear();
                                itemlist.addAll(list);
                                getAttachedView().getListSuccess(itemlist);
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



}
