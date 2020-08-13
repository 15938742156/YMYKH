package com.ykh.yinmeng.ymykh2.activity.yaoqinghuiyuan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.model.YaoqinghuiyuanResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.ImageUtil;
import com.ykh.yinmeng.ymykh2.utils.ZXingUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class YaoqinghuiyuanPresenterImp<V extends YaoqinghuiyuanMVPView> extends BasePresenter<V> implements YaoqinghuiyuanPresenter<V>{

    private Bitmap srcBitmap;
    private Bitmap qrBitmap;
    private String toSharePath = null;

    public YaoqinghuiyuanPresenterImp(){
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        ServerApi.<YaoqinghuiyuanResponse>inviteMember()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("udong", "获取二维码链接");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YaoqinghuiyuanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(YaoqinghuiyuanResponse response) {
                        if (response.getCode() == 1) {
                            int width = 1000;
                            if (srcBitmap != null) {
                                width = srcBitmap.getHeight()*3/16;
                            }
                            qrBitmap= ZXingUtils.createQRImage(response.getData(), width, width);
                            getAttachedView().getcontextSuccess(qrBitmap);
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
    public void onInit(Activity activity) {
        Intent intent = activity.getIntent();
        String img = intent.getStringExtra("backimg");
        getAttachedView().getBackgroundSuccess(img);
//        srcBitmap = BitmapFactory.decodeResource(activity.getResources(), Integer.valueOf(img));
    }

    @Override
    public void onToggleYaoqingBtn(Activity activity) {
        if (qrBitmap == null) {
            getAttachedView().showToast("获取二维码失败");
            return;
        }
        if (toSharePath == null) {
            toSharePath = ImageTools.joinBitmap(activity, srcBitmap, qrBitmap);
        }
        ImageUtil.compress2File(toSharePath, new ImageUtil.OnCompressFileListener() {
            @Override
            public void onCompressFile(String filePath) {
                toSharePath = filePath;
                if (!ImageUtil.isFileExists(toSharePath)) {
                    getAttachedView().showToast("获取二维码图片失败");
                } else {
                    getAttachedView().showShareView(toSharePath);
                }
            }
        });
    }
}
