package com.ykh.yinmeng.ymykh2.activity.addressadd;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citythreelist.ProvinceActivity;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.bean.AddressBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AddaddressPresenterImp<V extends AddaddressMVPView> extends BasePresenter<V> implements AddaddressPresenter<V> {
    private static final String TAG = "udong";
    Intent intent;
    AddressBean addressBean;

    public AddaddressPresenterImp() {
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
        intent = activity.getIntent();
        addressBean = intent.getParcelableExtra("AddressBean");
        int isDefault = -1;
        if (addressBean != null) {
            getAttachedView().setShrName(addressBean.getShrName());
            getAttachedView().setShrMobile(addressBean.getShrMobile());
            getAttachedView().setShrProvince(addressBean.getShrProvince());
            getAttachedView().setShrCity(addressBean.getShrCity());
            getAttachedView().setShrArea(addressBean.getShrArea());
            getAttachedView().setShrAddress(addressBean.getShrAddress());
            isDefault = addressBean.getIsDefault();
        }
        getAttachedView().setIsDefault(isDefault == 1);
    }

    @Override
    public void onToggleAddBtn(String shrName, String shrMobile, String shrProvince, String shrCity
            , String shrArea, String shrAddress, String isDefault) {
        if (addressBean == null) {
            ServerApi.<LzyResponse>addressAdd(shrName, shrMobile, shrProvince, shrCity
                    , shrArea, shrAddress, isDefault)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("udong", "新增地址");
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
                                getAttachedView().addAdressSuccess();
                            } else {
                                getAttachedView().addAdressFailure(response.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            getAttachedView().addAdressFailure(e.getLocalizedMessage());
                            getAttachedView().dismissDialog();
                        }

                        @Override
                        public void onComplete() {
                            getAttachedView().dismissDialog();
                        }
                    });
        } else {
            ServerApi.<LzyResponse>addressEdit(shrName, shrMobile, shrProvince, shrCity
                    , shrArea, shrAddress, isDefault, String.valueOf(addressBean.getId()))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(@NonNull Disposable disposable) {
                            Log.d("udong", "修改地址");
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
                                getAttachedView().editAdressSuccess();
                            } else {
                                getAttachedView().editAdressFailure(response.getMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            getAttachedView().editAdressFailure(e.getLocalizedMessage());
                            getAttachedView().dismissDialog();
                        }

                        @Override
                        public void onComplete() {
                            getAttachedView().dismissDialog();
                        }
                    });
        }
    }

    @Override
    public void onToggleCityPickerBtn(Activity activity) {

        getAttachedView().showDialog();
        /** 加载一级列表所有城市的数据*/
        CityListLoader.getInstance().loadCityData(activity);
        /** 加载三级列表显示省市区的数据*/
        CityListLoader.getInstance().loadProData(activity);

        Intent intent = new Intent(activity.getApplicationContext(), ProvinceActivity.class);
        activity.startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
        getAttachedView().dismissDialog();
    }
}
