package com.ykh.yinmeng.ymykh2.activity.kefuweixin;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.CreditsResponse;
import com.ykh.yinmeng.ymykh2.model.KefuResponse;
import com.ykh.yinmeng.ymykh2.model.MydataResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class KefuPresenterImp<V extends KefuMVPView> extends BasePresenter<V> implements KefuPresenter<V> {

    private List<String> list = new ArrayList<>();
    List<String> weixinlist;

    public KefuPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);

        ServerApi.<KefuResponse>kefu()
                .subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) {
                Log.d("xinhui", "客服微信");
                getAttachedView().showDialog();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<KefuResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                getCompositeDisposable().add(d);
            }

            @Override
            public void onNext(KefuResponse response) {
                if (response.getCode() == 1) {
                    weixinlist = response.getData();
                    if (weixinlist != null){
                        list.clear();
                        list.addAll(weixinlist);
                        getAttachedView().getListSuccess(list);
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onToggleCopyBtn(Activity activity,int position) {
        ClipboardManager copy;
        copy = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
        copy.setText(weixinlist.get(position));
        ToastUtils.showToast(activity, "复制成功", Toast.LENGTH_SHORT);
    }
}
