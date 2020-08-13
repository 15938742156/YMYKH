package com.ykh.yinmeng.ymykh2.activity.yaoqing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.yaoqinghuiyuan.YaoqinghuiyuanActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.BackgroundResponse;
import com.ykh.yinmeng.ymykh2.model.YaoqinghuiyuanResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.ImageUtil;
import com.ykh.yinmeng.ymykh2.utils.ZXingUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class YaoqingPresenterImp<V extends YaoqingMVPView> extends BasePresenter<V> implements YaoqingPresenter<V> {

    private List<BackgroundResponse.DataBean> list=new ArrayList<>();

    public YaoqingPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<BackgroundResponse>background()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取背景图片");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BackgroundResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(BackgroundResponse response) {
                        if (response.getCode() == 1) {
                            List<BackgroundResponse.DataBean> backlist = response.getData();
                            if (list != null){
                                list.clear();
                                list.addAll(backlist);
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
    public void onToggleItem(Activity activity,int position) {
        Intent intent = new Intent(activity, YaoqinghuiyuanActivity.class);
        intent.putExtra("backimg",list.get(position).getLogo());
        activity.startActivity(intent);
    }


}
