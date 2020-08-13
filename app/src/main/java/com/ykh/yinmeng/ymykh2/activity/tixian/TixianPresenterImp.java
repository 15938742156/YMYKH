package com.ykh.yinmeng.ymykh2.activity.tixian;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.TixianjiluActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.cardlist.CardguanliActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.CardBean;
import com.ykh.yinmeng.ymykh2.model.CardResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.MD5.MD5Util;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TixianPresenterImp<V extends TixianMVPView> extends BasePresenter<V> implements TixianPresenter<V> {

    private static final int REQUEST_CODE_SELECT_CARD = 269;

    private List<CardBean> cardList;
    private CardBean card = null;
    private String moneyBalance = null;
    private String withdrawalMoney;
    long time = System.currentTimeMillis()/1000;//获取系统当前时间
    String m = "eyJpYXQiOjE1NjAtcL25sTkdJblg0M1wvWVZmc2RjQSCJuYmYiOjE1zA5MTc5NCwiZGF0YSI6eyJ"+time;
    String a = MD5Util.getStringMD5(m);
    String c = MD5Util.getStringMD5(a).toUpperCase();

    public TixianPresenterImp(){
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
    public void onInit(Activity activity) {
        getData();
        Log.d("xinhui", String.valueOf(card.getId()));
    }

    @Override
    public void onConfirmBtn(final Activity activity) {
        double total,aFloat;
        try {
            aFloat = Double.valueOf(withdrawalMoney);
            total = Double.valueOf(moneyBalance);

        } catch (Exception e) {
            total = 0.0f;
            aFloat = 0.0f;
            ToastUtils.showToast(activity, "请输入提现金额", Toast.LENGTH_SHORT);
        }
        if (total == 0.0f) {
            ToastUtils.showToast(activity, "没有可提现的金额", Toast.LENGTH_SHORT);
            return;
        }
        if (total < aFloat) {
            ToastUtils.showToast(activity, "提现的金额不能大于可提现的金额", Toast.LENGTH_SHORT);
            return;
        }
        if (Double.valueOf(withdrawalMoney) < 100) {
            ToastUtils.showToast(activity, "提现的金额不能小于100元", Toast.LENGTH_SHORT);
            return;
        }
        if (String.valueOf(card.getId()) != null || TextUtils.isEmpty(String.valueOf(card.getId())) || TextUtils.equals("",String.valueOf(card.getId()))){
            ServerApi.<LzyResponse>carddrawmoney(String.valueOf(card.getBanksNumber().replace(" ","")),withdrawalMoney,String.valueOf(time),c)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("xinhui", "确认提现");
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
                                getAttachedView().getDataSuccess("提现成功");
                                Intent intent = new Intent(activity, TixianjiluActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
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
        }else {
            ToastUtils.showToast(activity,"请添加储蓄卡",Toast.LENGTH_LONG);
        }


    }

    @Override
    public void updateCard(CardBean card) {

    }
    @Override
    public void confirmPayPwd(String pwd) {
        ServerApi.<LzyResponse>confirmPayPwd(pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "确认交易密码");
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
                            getAttachedView().onPwdCorrect();
                            Log.d("xinhui", "确认交易密码成功");
                        } else {
                            getAttachedView().onPwdErro(response.getMsg());
                            Log.d("xinhui", "确认交易密码失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().onPwdErro(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onZhifubaoBtn(final Activity activity) {
        ServerApi.<LzyResponse>zhifubaomoney(withdrawalMoney,String.valueOf(time),c)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "支付宝提现");
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
                            getAttachedView().OnZhifubaoSuccess(response.getMsg());
                            Log.d("xinhui", "支付宝提现成功");
                            Intent intent = new Intent(activity, TixianjiluActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        } else {
                            getAttachedView().OnZhifubaoFailure(response.getMsg());
                            Log.d("xinhui", "支付宝提现失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().OnZhifubaoFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }


    @Override
    public void onTextChanged(String text) {
        withdrawalMoney = text;
    }

    @Override
    public void onToggleSelectCardBtn(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), CardguanliActivity.class);
        intent.putExtra("selectCard", true);
        activity.setResult(Activity.RESULT_OK, intent);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT_CARD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_SELECT_CARD) {
            //选择银行卡成功
            String cardID = data.getStringExtra("CardID");
            if (!TextUtils.isEmpty(cardID)) {
                for (CardBean cardBean : cardList) {
                    if (cardID.equals(cardBean.getId())) {
                        card = cardBean;
                        getAttachedView().updateCard(card);
                        break;
                    }
                }
            }else {
                ToastUtils.showToast(App.getContext(),"请添加储蓄卡",Toast.LENGTH_LONG);
            }
        }
    }

    private void getData() {
        ServerApi.<CardResponse>bankcardList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取银行卡列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CardResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(CardResponse cardResponse) {
                        if (cardResponse.getCode() == 1) {
                            cardList = cardResponse.getData();
                            if (cardList != null){
                                if (cardList.size() > 0) {
                                    card = cardList.get(0);
                                    for (CardBean cardBean : cardList) {
                                        if (cardBean.getStatus() == 1) {
                                            card = cardBean;
                                            break;
                                        }
                                    }
                                    getAttachedView().updateCard(card);
                                }
                            }
                        } else {
                            getAttachedView().getDataFailure(cardResponse.getMsg());
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

        ServerApi.<LzyResponse>getBlance()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取账户余额详情");
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
                            moneyBalance = response.getData().toString();
                            getAttachedView().getMoneySuccess(moneyBalance);//可用余额
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
