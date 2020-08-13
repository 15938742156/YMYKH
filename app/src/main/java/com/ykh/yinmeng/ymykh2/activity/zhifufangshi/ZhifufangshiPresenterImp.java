package com.ykh.yinmeng.ymykh2.activity.zhifufangshi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.jifenguanli.JifenguanliActivity;
import com.ykh.yinmeng.ymykh2.activity.myorder.MyOrderActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.WXResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 2018/11/10 15:05
 * Description：
 */
public class ZhifufangshiPresenterImp<V extends ZhifufangshiMVPView> extends BasePresenter<V> implements ZhifufangshiPresenter<V>,IWXAPIEventHandler {

    String out_trade_no = null;
    String pay_money = null;
    String pay_type  = null;
    private IWXAPI api;
    Intent intent;

    public ZhifufangshiPresenterImp() {
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
        Intent intent = activity.getIntent();
        out_trade_no = intent.getStringExtra("out_trade_no");
        pay_money = intent.getStringExtra("pay_money");
        pay_type = intent.getStringExtra("pay_type");
        getAttachedView().showPayMoney(pay_money);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onToggleEnsureBtn(final Activity activity) {
        ServerApi.<LzyResponse>payAL(out_trade_no)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "支付宝支付");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LzyResponse>() {
                    @Override
                    public void accept(LzyResponse response) throws Exception {
                        if (response.getCode() == 1) {
                        } else {
                            getAttachedView().showErro(response.getMsg());
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<LzyResponse, ObservableSource<Map<String, String>>>() {
                    @Override
                    public ObservableSource<Map<String, String>> apply(final LzyResponse response) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
                            @Override
                            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                                if (response.getCode() == 1) {

                                    PayTask alipay = new PayTask(activity);
                                    Map<String, String> result = alipay.payV2(response.getData().toString(),true);

                                    emitter.onNext(result);
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Exception(response.getMsg()));
                                }
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(Map<String, String> response) {

                        Log.i("WWWWWWWWWWWWW",response.get("resultStatus"));
                        if (TextUtils.equals("9000",response.get("resultStatus"))){
                            getAttachedView().paySuccess("订单支付成功");
                            if (TextUtils.equals("pay_type","1")){
                                activity.finish();
                                Intent intent = new Intent(activity, JifenguanliActivity.class);
                                activity.startActivity(intent);
                            }else {
                                activity.finish();
                                Intent intent = new Intent(activity, MyOrderActivity.class);
                                activity.startActivity(intent);
                            }
                        }else {
                            getAttachedView().paySuccess("订单支付失败");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showErro(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onWeixinBtn(final Activity activity) {

        ServerApi.<WXResponse>Wxpay(out_trade_no)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "微信支付");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<WXResponse>() {
                    @Override
                    public void accept(WXResponse response) throws Exception {
                        if (response.getCode() == 1) {
                        } else {
                            getAttachedView().showErro(response.getMsg());
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<WXResponse, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(final WXResponse response) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                if (response.getCode() == 1) {
                                    Log.d("udong", "支付data：" + String.valueOf(response.getData()));
                                    WXResponse.DataBean data = response.getData();
                                    PayReq req = new PayReq();
                                    //req.appId = "wxf8b4f85f3a794e77";
                                    api = WXAPIFactory.createWXAPI(activity, data.getAppid());
                                    api.handleIntent(new Intent(), ZhifufangshiPresenterImp.this);
                                    req.appId			= data.getAppid();
                                    req.partnerId		= data.getPartnerid();
                                    req.prepayId		= data.getPrepayid();
                                    req.nonceStr		= data.getNoncestr();
                                    req.timeStamp		= String.valueOf(data.getTimestamp());
                                    req.packageValue	= data.getPackageX();
                                    req.sign			= data.getSign();
                                    req.extData			= "app data"; // optional
                                    api.sendReq(req);
                                } else {
                                    emitter.onError(new Exception(response.getMsg()));
                                }
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(String response) {

                        Log.i("udong",response);
                        getAttachedView().dismissDialog();

                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showErro(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });

    }

    @Override
    public void onReq(BaseReq baseReq) {
        switch (baseReq.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                getAttachedView().showErro(((ShowMessageFromWX.Req) baseReq).message.title);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("udong", "onPayFinish, errCode = " + resp.errCode);

        int result = 0;

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }

        getAttachedView().showErro(App.getContext().getString(result) + ", type=" + resp.getType());

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d("udong", "onPayFinish, errCode = " + App.getContext().getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            if (resp.errCode == 0) {
                getAttachedView().paySuccess("订单支付成功");
            } else if (resp.errCode == 1) {
                getAttachedView().showErro("订单支付失败");
            } else if (resp.errCode == 2) {
                getAttachedView().showErro("取消支付");
            } else {
                getAttachedView().showErro("支付异常");
            }
        }
    }


}
