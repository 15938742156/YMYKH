package com.ykh.yinmeng.ymykh2.fragment.mefragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.reflect.TypeToken;
import com.lancewu.imagepicker.ImagePicker;
import com.lancewu.imagepicker.ImagePickerResult;
import com.lancewu.imagepicker.OnImagePickerCallback;
import com.lancewu.imagepicker.util.StreamUtils;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.shiming.ShimingActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.model.MydataResponse;
import com.ykh.yinmeng.ymykh2.network.Convert;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.utils.AppUtils;
import com.ykh.yinmeng.ymykh2.utils.Constant;
import com.ykh.yinmeng.ymykh2.utils.ImageTools;
import com.ykh.yinmeng.ymykh2.utils.SharedPreferencesUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

public class MyPresenterImp<V extends MyMVPView> extends BasePresenter<V> implements MyPresenter<V> {

    private WeakReference<Activity> reference;

    private String avaterPath;//缓存头像路径
    private ImagePicker mPicker;


    public MyPresenterImp(){
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
    public void onInit(final Activity activity) {
        reference = new WeakReference<>(activity);
        avaterPath = activity.getExternalCacheDir() + File.separator + "user_head_img.jpg";
        showRound(avaterPath);
        ServerApi.<MydataResponse>getcenter().subscribeOn(Schedulers.io()).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) {
                Log.d("xinhui", "获取用户个人数据");
                getAttachedView().showDialog();
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<MydataResponse>() {
            @Override
            public void accept(MydataResponse response) throws Exception {
                if (response.getCode() == 1) {
                    Object rankInfo = response.getData().getRankInfo();
                    MydataResponse.DataBean.UserInfoBean userBean = response.getData().getUserInfo();//自己
                    MydataResponse.DataBean.RankInfoBean infoBean = response.getData().getRankInfo();//上级
                    MydataResponse.DataBean.ExtendBean extendBean = response.getData().getExtend();//身份证
                    if (rankInfo != null) {
                            if (infoBean != null) {
                                getAttachedView().showNameSuccess("推荐人：" + infoBean.getName());
                                getAttachedView().showTelSuccess("推荐人电话:" + infoBean.getTel());

                            }
                    } else {
                            getAttachedView().showTelSuccess("推荐人电话: 无上级");//同时隐藏电话图标
                            getAttachedView().showNameSuccess("推荐人 新汇平台");//同时显示皇冠图标
                    }

                    int isReal = response.getData().getUserInfo().getIs_real();
                    SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_AUTHENTICATION, isReal);
                    SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_LEVEL, userBean.getLevel());

                    if (extendBean != null) {
                        SharedPreferencesUtils.getInstance(App.getContext()).put(Constant.SHARED_NAME, extendBean.getIdName());
                        Log.v("000000000",extendBean.getIdName());

                    }

                    Log.v("0000000",SharedPreferencesUtils.getInstance(App.getContext()).get(Constant.SHARED_NAME));
                    if (isReal != 1 && !AppUtils.isActivityTop(ShimingActivity.class, activity)) {
                        activity.startActivity(new Intent(App.getContext(), ShimingActivity.class));
                    }
                } else {
                    getAttachedView().showToast(response.getMsg());
                }
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<MydataResponse, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(final MydataResponse mydataResponse) throws Exception {
                return Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        MydataResponse.DataBean.UserInfoBean userBean = mydataResponse.getData().getUserInfo();
                        if (userBean != null && !TextUtils.isEmpty(userBean.getImg())) {
                                String headImg = userBean.getImg();
                                Bitmap bitmap;
                                if (headImg.startsWith("http")) {
                                    Log.e("udong", "获取到的图片是一个地址");
                                    bitmap = decodeUriAsBitmapFromNet(headImg);
                                } else {
                                    Log.i("udong", "获取到的图片是base64数据");
                                    bitmap = ImageTools.base64ToBitmap(headImg);
                                }
                                saveImage(bitmap);
//                                bitmap.recycle();
                                emitter.onNext(avaterPath);

                        }
                        emitter.onComplete();
                    }
                });
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(String avaterPath) {
                        showRound(avaterPath);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showToast(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }

    @Override
    public void paiZhao(Activity activity) {
        // 从相机拍照并裁剪
        File file = new File(activity.getExternalCacheDir(), "camera_crop.jpg");
        // 创建裁剪参数
        ImagePicker.CropConfigBuilder cropConfigBuilder = new ImagePicker.CropConfigBuilder()
                .aspect(1, 1) // 比例1：2
                .outputSize(250, 250) // 输出大小200*400
                .outputFile(file);  // 最终文件保存路径
        // 创建选择器
        mPicker = new ImagePicker.Builder(activity)
                .fromCamera(file) // 表示从相机选择，并设置拍照保存文件
                .withCrop(cropConfigBuilder) // 拍照完紧接裁剪
                .build();
        // 调用选图
        mPicker.pick(mCallback);
    }
    @Override
    public void xuanZeTuPian(Activity activity) {
        File file = new File(activity.getExternalCacheDir(), "gallery_crop.jpg");
        ImagePicker.CropConfigBuilder cropConfigBuilder = new ImagePicker.CropConfigBuilder()
                .aspect(1, 1)
                .outputSize(250, 250) // 采用默认大小200
                .outputFile(file);
        mPicker = new ImagePicker.Builder(activity)
                .fromGallery()
                .withCrop(cropConfigBuilder)
                .build();
        mPicker.pick(mCallback);
    }

    public void uploadHeader(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            return;
        }
        ServerApi.<LzyResponse>uploadBase64Images(bitmap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.i("xinhui", "上传头像图片");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<LzyResponse>() {
                    @Override
                    public void accept(LzyResponse response) throws Exception {
                        if (response.getCode() == 1) {
                            Log.i("xinhui", "上传头像图片成功");
                            String data = response.getData().toString();
                            Log.e("xinhui", "data:path:" + data);
                        } else {
                            //上传失败
                            Log.i("xinhui", "上传头像图片失败 code:" + response.getCode());
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<LzyResponse, ObservableSource<LzyResponse>>() {
                    @Override
                    public ObservableSource<LzyResponse> apply(LzyResponse response) {
                        return ServerApi.<LzyResponse>changeuserHeadImg(response.getData().toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "更换头像");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            Log.d("udong", "更换头像成功");
                        } else {
                            Log.d("udong", "更换头像失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showToast(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });
    }


    private OnImagePickerCallback mCallback = new OnImagePickerCallback() {
        @Override
        public void onPickError(@OnImagePickerCallback.ErrorCode int errorCode) {
            // 发生错误，具体错误参考：@ErrorCode
            getAttachedView().showToast("发生错误：" + errorCode);
        }

        @Override
        public void onPickSuccess(@android.support.annotation.NonNull ImagePickerResult result) {
            // 选图/裁剪回调
            InputStream inputStream = null;
            try {
                // 从选择结果中取出文件Uri，进行想要的处理，这边直接显示
                Activity activity = reference.get();
                if (activity == null) {
                    return;
                }
                inputStream = activity.getContentResolver().openInputStream(result.getImageUri());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap == null) {
                    //TODO 可能是为null了
                    return;
                }
                Bitmap crop_bitmap = toRoundBitmap(bitmap);
                if (crop_bitmap != null) {
//                    setPicToView(crop_bitmap);// 保存在SD卡中
                    getAttachedView().showUserHeadImg(crop_bitmap);
                    /**
                     * 上传服务器代码
                     */
                    String path = result.getImageUri().getPath();
                    Log.e("udong onPickSuccess", path);
                    ImageTools.savePhotoToCacheDir(bitmap, avaterPath);
                    if (bitmap != null && !bitmap.isRecycled()) {
//                        bitmap.recycle();
                    }
                    uploadHeader(path);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                StreamUtils.close(inputStream);
            }
        }

        @Override
        public void onPickCancel() {
            // 主动取消选择/裁剪时回调
            getAttachedView().showToast("取消选择");
        }

    };

    private void showRound(String avaterPath) {
        File avaterFile = new File(avaterPath);
        if (avaterFile.exists()) {
            Bitmap headerBitmap = BitmapFactory.decodeFile(avaterPath);
            Bitmap roundBitmap = toRoundBitmap(headerBitmap);
            getAttachedView().showUserHeadImg(roundBitmap);
        }
    }

    /**
     * 设置图片为圆形
     */
    private Bitmap toRoundBitmap(Bitmap crop_bitmap) {
        if (crop_bitmap == null) {
            return null;
        }
        crop_bitmap = scaleBitmap(crop_bitmap);
        int width = crop_bitmap.getWidth();
        int height = crop_bitmap.getHeight();
        int r = 0;
        //取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;

        }
        //构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        //设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(crop_bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 把图片缩小到250*250
     * @param origin
     * @return
     */
    private Bitmap scaleBitmap(Bitmap origin) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        int cropWidth = width >= height ? height : width;
        float ratio = 1.0f;
        if (cropWidth > 250) {
            ratio = 250/(cropWidth * 1.0f);
        }
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
//        origin.recycle();
        return newBM;
    }

    public static Bitmap decodeUriAsBitmapFromNet(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    public void saveImage(Bitmap image) {
        //判断sd卡是否处于挂载状态
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(avaterPath);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
