package com.ykh.yinmeng.ymykh2.activity.address;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;


import com.ykh.yinmeng.ymykh2.activity.addressadd.AddaddressActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.model.AddressResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
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
public class AddressPresenterImp<V extends AddressMVPView> extends BasePresenter<V> implements AddressPresenter<V> {

    private static final int REQUEST_CODE_ADDRESS = 169;
    private static final int REQUEST_CODE_EDIT_ADDRESS = 170;
    private List<AddressBean> addressList = new ArrayList<>();
    private boolean selectAddress = false;

    public AddressPresenterImp() {
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
        selectAddress = intent.getBooleanExtra("selectAddress", false);
        getData();
    }

    @Override
    public void onToggleTianjiaBtn(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(),AddaddressActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE_ADDRESS);
    }

    @Override
    public void onToggleDeleteBtn(final int position) {
        ServerApi.<LzyResponse>addressDel(String.valueOf(addressList.get(position).getId()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "删除地址");
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

                            getAttachedView().delAddressSuccess();
                            addressList.remove(position);
                            getAttachedView().onItemRemoved(position);
                        } else {
                            getAttachedView().delAddressFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().delAddressFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void onToggleEditBtn(Activity activity, int position) {
        Intent intent = new Intent(activity.getApplicationContext(), AddaddressActivity.class);
        intent.putExtra("AddressBean",addressList.get(position));
        activity.startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
    }

    @Override
    public void onToggleItem(Activity activity, int position) {
        Intent intent;
        if (selectAddress) {
            intent = new Intent();
            intent.putExtra("AddressBean",addressList.get(position));
            activity.setResult(Activity.RESULT_OK,intent);
            activity.finish();
        } else {
            intent = new Intent(activity.getApplicationContext(), AddaddressActivity.class);
            intent.putExtra("AddressBean",addressList.get(position));
            activity.startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_ADDRESS) {
            //更新地址成功，更新界面
            getData();
        } else if (requestCode == REQUEST_CODE_EDIT_ADDRESS){
            getData();
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
                                addressList.clear();
                                addressList.addAll(list);
                                getAttachedView().getListSuccess(addressList);
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
