package com.ykh.yinmeng.ymykh2.activity.querendingdan;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ykh.yinmeng.ymykh2.activity.address.AddressActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.myorder.MyOrderActivity;
import com.ykh.yinmeng.ymykh2.activity.zhifufangshi.ZhifufangshiActivity;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.bean.POSStoreBean;
import com.ykh.yinmeng.ymykh2.model.AddressResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.SubmitOrderResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.ToastUtils;

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
public class QuerendingdanPresenterImp<V extends QuerendingdanMVPView> extends BasePresenter<V> implements QuerendingdanPresenter<V> {

    private static final int REQUEST_CODE_SELECT_ADDRESS = 269;
    private AddressBean address = null;
    float totalPice = 0.00f;//机具总价格
    float deductionPice = 0.00f;//抵扣金额
    float balanceTotalPice = 0.00f;//结算金额
    float nowPrice = 0.00f;//当前余额
    int num;
    int type;
    String title = null;//名称
    double yun;//产品运费
    double money;//产品押金
    String gid = null;//该产品id
    String img;//产品图片
    String miaoshu = null;//描述
    String gz_num = null;//关注数量
    public QuerendingdanPresenterImp() {
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
        title = String.valueOf(intent.getExtras().getString("title"));
        yun = Double.valueOf(intent.getExtras().getDouble("yun"));
        money = Double.valueOf(intent.getExtras().getDouble("money"));
        num = Integer.valueOf(intent.getExtras().getString("num"));
        gid = String.valueOf(intent.getExtras().getInt("gid"));
        img = String.valueOf(intent.getExtras().getString("img"));
        miaoshu = String.valueOf(intent.getExtras().getString("miaoshu"));
        gz_num = String.valueOf(intent.getExtras().getInt("gz_num"));
        if (title != null){
            getAttachedView().showTitleSuccess(title);
        }else {
            getAttachedView().showTitleSuccess("");
        }
        getAttachedView().showYunSuccess(String.valueOf(yun));
        if (String.valueOf(money) != null){
            getAttachedView().showMoneySuccess(String.valueOf(money));
        }else {
            getAttachedView().showMoneySuccess("0.00");
        }
        getAttachedView().showImgSuccess("http://wx.ykh9.com"+img);
        if (miaoshu != null){
            getAttachedView().showMiaoshuSuccess(miaoshu);
        }else {
            getAttachedView().showMiaoshuSuccess("");
        }
        if (gz_num != null){
            getAttachedView().showGznumSuccess(gz_num);
        }else {
            getAttachedView().showGznumSuccess("0");
        }
        getAttachedView().showNumSuccess(String.valueOf(num));
        totalPice = Float.valueOf(String.valueOf(money)) * num+Float.valueOf(String.valueOf(yun));//总价格 = 单价 x 数量
        getAttachedView().updateTotalPice(String.format("%.2f",totalPice));
        if (nowPrice <= deductionPice){
            balanceTotalPice = totalPice - deductionPice;
            getAttachedView().updateBalanceTotalPice(String.format("%.2f",totalPice));
        }else {
            getAttachedView().getDataFailure("抵扣金额需小于当前余额");
        }
        Log.v("hhhhhhhhhhhhhhhhhh",title+""+yun+""+money+""+num+""+gid+img);
        getData();
        getNowBalance();
    }

    @Override
    public void onToggleSelectAddressBtn(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), AddressActivity.class);
        intent.putExtra("selectAddress", true);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT_ADDRESS);
    }

    @Override
    public void onToggleEnsureBtn(final Activity activity) {
        ServerApi.<SubmitOrderResponse>orderPlace(String.valueOf(gid),String.valueOf(num),String.valueOf(type),String.valueOf(address.getId()),String.valueOf(deductionPice))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "提交订单");
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
                            if (response.getData().getIs_pay() == 1){
                                Intent intent = new Intent(activity, MyOrderActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                                ToastUtils.showToast(activity,"订单已成功", Toast.LENGTH_SHORT);
                            }else {
                                Intent intent = new Intent(activity, ZhifufangshiActivity.class);
                                intent.putExtra("out_trade_no", response.getData().getOut_trade_no());
                                intent.putExtra("pay_money", response.getData().getPay_money());
                                activity.startActivity(intent);
                                activity.finish();
                            }

                        } else {
                            getAttachedView().orderPlaceFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().orderPlaceFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }


    @Override
    public void onEditTextChange(CharSequence charSequence) {
        try {
            deductionPice = Float.valueOf(String.valueOf(charSequence));
        } catch (Exception e) {
            deductionPice = 0.00f;
        }
        balanceTotalPice = totalPice - deductionPice;
        getAttachedView().updateBalanceTotalPice(String.format("%.2f",balanceTotalPice));
    }

    @Override
    public void confirmPayPwd(String pwd) {
        ServerApi.<LzyResponse>confirmPayPwd(pwd)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "确认交易密码");
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
                            Log.d("udong", "确认交易密码成功");
                        } else {
                            getAttachedView().onPwdErro(response.getMsg());
                            Log.d("udong", "确认交易密码失败");
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
    public void getNowBalance() {
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
                            getAttachedView().getNowbalancePrice(response.getData().toString());//当前账户余额
                        } else {
                            getAttachedView().orderPlaceFailure(response.getMsg());//
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().orderPlaceFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onToggleBtn(int type) {
        this.type = type;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_SELECT_ADDRESS) {
            //更新地址成功，更新界面
            AddressBean addressBean = data.getParcelableExtra("AddressBean");
            if (addressBean != null) {
                address = addressBean;
                getAttachedView().updateAddress(address);
            }
        }
    }

    public void getData() {
        ServerApi.<AddressResponse>addressList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取地址列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(AddressResponse response) {
                        if (response.getCode() == 1) {
                            List<AddressBean> list = response.getData();
                            if (list != null) {
                                if (list.size() > 0) {
                                    address = list.get(0);
                                    for (AddressBean addressBean : list) {
                                        if (addressBean.getIsDefault() == 1) {
                                            address = addressBean;
                                            break;
                                        }
                                    }
                                    getAttachedView().updateAddress(address);
                                }
                            }
                        } else {
                            getAttachedView().getAdressFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().getAdressFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

}
