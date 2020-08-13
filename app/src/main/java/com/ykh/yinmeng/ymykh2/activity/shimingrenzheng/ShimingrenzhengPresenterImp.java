package com.ykh.yinmeng.ymykh2.activity.shimingrenzheng;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.baidu.ocr.ui.util.ImageUtil;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.setjiaoyipassword.SetjiaoyipasswordActivity;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShimingrenzhengPresenterImp<V extends ShimingrenzhengMVPView> extends BasePresenter<V> implements ShimingrenzhengPresenter<V> {

    IDCardBean resultZM = null;
    IDCardBean resultFM = null;
    String pathZm = null;
    String pathFm = null;
    public ShimingrenzhengPresenterImp() {
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
        Bundle extras = intent.getExtras();
        resultZM = extras.getParcelable("resultZM");
        resultFM = extras.getParcelable("resultFM");
        pathZm = extras.getString("pathZm");
        pathFm = extras.getString("pathFm");

        String name = resultZM.getName();
        String idNumber = resultZM.getIdNumber();
        String phone = SharedPreferencesUtils.getInstance(activity).get(Constant.SHARED_PHONE);
        getAttachedView().showTel(phone);
        getAttachedView().showName(name);
        getAttachedView().showCard(idNumber);


        Bitmap bitmapZm = ImageUtil.decodeImage(pathZm);
        Bitmap bitmapFm = ImageUtil.decodeImage(pathFm);

        if (bitmapZm != null) {
            getAttachedView().showZhengMian(bitmapZm);
        }
        if (bitmapFm != null) {
            getAttachedView().showFanMian(bitmapFm);
        }
    }

    @Override
    public void onToggleNext(Activity activity) {
        uploadUserInfo();
    }

    @Override
    public void shimingSuccess(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), SetjiaoyipasswordActivity.class);
        intent.putExtra("resultZM",resultZM);
        activity.startActivity(intent);
        activity.finish();
    }


    private void uploadUserInfo() {
        ServerApi.<LzyResponse>uploadUserInfo(resultZM, resultFM)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.i("Shiming", "上传身份证信息");
//                        getAttachedView().showDialog();
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
                            Log.i("Shiming", "上传身份证信息成功");
                            getAttachedView().shimingSuccess();
//                            Type type = new TypeToken<String>() {
//                            }.getType();
//                            String data = Convert.fromJson(response.getData().toString(), type);
//                            result.setUrl(data);
//                            Log.e("Login", "data:path:" + data);
                        } else {
                            //上传失败
                            getAttachedView().showAlert("实名认证失败", response.getMsg());
                            Log.i("Shiming", "上传身份证信息失败 code:" + response.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showAlert("实名认证失败", "网络错误");
                        Log.e("Shiming", "上传图片失败，网络错误");
                        e.printStackTrace();
//                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
//                        getAttachedView().dismissDialog();
                    }
                });
    }
}
