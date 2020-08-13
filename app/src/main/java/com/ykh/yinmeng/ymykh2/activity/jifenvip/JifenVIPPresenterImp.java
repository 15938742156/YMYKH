package com.ykh.yinmeng.ymykh2.activity.jifenvip;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.zhifufangshi.ZhifufangshiActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.JifenVIPResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.MydataResponse;
import com.ykh.yinmeng.ymykh2.model.SubmitOrderResponse;
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

public class JifenVIPPresenterImp<V extends JifenVIPMVPView> extends BasePresenter<V> implements JifenVIPPresenter<V> {

    String gold = null;
    String jewel = null;
    String upgrade = null;
    String level;
    String money;
    int type = 1;
    int level1 = SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_LEVEL,0);

    public JifenVIPPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);

        ServerApi.<JifenVIPResponse>vipprice().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) {
                Log.d("xinhui", "积分vip价格");
                getAttachedView().showDialog();
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<JifenVIPResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                getCompositeDisposable().add(d);
            }

            @Override
            public void onNext(JifenVIPResponse response) {
                if (response.getCode() == 1) {
                    gold = response.getData().getGold();
                    jewel = response.getData().getJewel();
                    upgrade = response.getData().getUpgrade();
                    getAttachedView().showVIPHuangjinSuccess(gold);
                    getAttachedView().showVIPzuanshiSuccess(jewel);

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
    public void OnChongzhiBtn(final Activity activity , int type) {
        if (type == 1){
            if (level1 == 0){
                this.level = "gold";
                this.money = gold;
            }
            if (level1 == 1){
                ToastUtils.showToast(activity,"您已是黄金会员，请购买钻石会员！", Toast.LENGTH_SHORT);
            }
        }
        if (type == 2){
            if (level1 == 0){
                this.level = "jewel";
                this.money = jewel;
            }
            if (level1 == 1){
                this.level = "upgrade";
                this.money = upgrade;
            }
            if (level1 == 2){
                ToastUtils.showToast(activity,"您已是钻石会员，无需升级！", Toast.LENGTH_SHORT);
            }
        }
        ServerApi.<SubmitOrderResponse>vipchongzhi(level,money)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "vip充值");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubmitOrderResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(SubmitOrderResponse response) {
                        if (response.getCode() == 1) {
                            Intent intent = new Intent(App.getContext(), ZhifufangshiActivity.class);
                            intent.putExtra("out_trade_no", response.getData().getOut_trade_no());
                            intent.putExtra("pay_money", response.getData().getPay_money());
                            intent.putExtra("pay_type", "1");
                            activity.startActivity(intent);
                            activity.finish();
                            Log.d("xinhui", "vip充值成功");
                        } else {
                            getAttachedView().getDataFailure(response.getMsg());
                            Log.d("xinhui", "vip充值失败");
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
    public void OnFreeBtn(Activity activity, String type) {
        ServerApi.<LzyResponse>vipfree(type)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "vip免费升级");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            getAttachedView().getVIPSuccess(response.getMsg());
                            Log.d("xinhui", "vip申请成功");
                        } else {
                            getAttachedView().getDataFailure(response.getMsg());
                            Log.d("xinhui", "vip申请失败");
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
