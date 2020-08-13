package com.ykh.yinmeng.ymykh2.activity.jifensubmit;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lancewu.imagepicker.ImagePicker;
import com.lancewu.imagepicker.ImagePickerResult;
import com.lancewu.imagepicker.OnImagePickerCallback;
import com.lancewu.imagepicker.util.StreamUtils;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.jifenjindu.JifenjinduActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.model.JifenItemgoodsResponse;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.DialogPopupwindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SubmitPresenterImp<V extends SubmitMVPView> extends BasePresenter<V> implements SubmitPresenter<V> {

    private List<JifenItemgoodsResponse.DataBean> itemlist = new ArrayList<>();
    Intent intent;
    int type;
    String id;
    String gid;
    private ImagePicker mPicker;
    private WeakReference<Activity> reference;
    String data;
    String address;


    public SubmitPresenterImp(){
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
        reference = new WeakReference<>(activity);
        intent = activity.getIntent();
        intent.getBooleanExtra("goods",true);
        type = Integer.valueOf(intent.getExtras().getInt("typee"));
        id = String.valueOf(intent.getExtras().getString("id"));
        getAttachedView().getTypeSuccess(type);
        getData();
        Log.v("typetypetype",type+"");
    }
    public void getData(){
        ServerApi.<JifenItemgoodsResponse>goodslist(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "分类商品列表");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JifenItemgoodsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(JifenItemgoodsResponse response) {
                        if (response.getCode() == 1) {
                            List<JifenItemgoodsResponse.DataBean> list = response.getData();
                            if (list != null){
                                itemlist.clear();
                                itemlist.addAll(list);
                                getAttachedView().getListSuccess(itemlist);
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

    @Override
    public void onToggleItem(Activity activity, int position) {

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
                        Log.i("xinhui", "上传图片");
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
                            Log.i("xinhui", "上传图片成功");
                            data = response.getData().toString();
                            Log.e("xinhui", "data:path:"+data);
                        } else {
                            //上传失败
                            Log.i("xinhui", "上传图片失败 code:" + response.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("xinhui", "上传图片失败，网络错误");
                        e.printStackTrace();
//                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
//                        getAttachedView().dismissDialog();
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
                if (bitmap != null) {
                    getAttachedView().showUserHeadImg(bitmap);
                    /**
                     * 上传服务器代码
                     */
                    String path = result.getImageUri().getPath();
                    Log.e("xinhui onPickSuccess", path);
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

    public void onExchangeBt(final Activity activity, String gid, String sn, String converPic, String mark){
        ServerApi.<LzyResponse>duihuantijiao(gid,sn,data,mark)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.d("xinhui", "兑换表单提交");
                        getAttachedView().showDialog();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LzyResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(LzyResponse response) {
                        if (response.getCode() == 1) {
                            address = response.getData().toString();
                            if (type == 2){
                                new DialogPopupwindow(activity)
                                        .setTitle("确认收货地址")
                                        .setContent(address)
                                        .setConfirmText("复制并完成")
                                        .setClickListener(new DialogPopupwindow.ViewClickListener() {
                                            @Override
                                            public void confirm(View view) {
                                                ClipboardManager copy;
                                                // 从API11开始android推荐使用android.content.ClipboardManager
                                                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                                                copy = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
                                                copy.setText(address);
                                                Toast.makeText(activity,"复制成功",Toast.LENGTH_LONG).show();
                                                activity.finish();
                                                Intent intent = new Intent(activity, JifenjinduActivity.class);
                                                activity.startActivity(intent);
                                            }
                                        }).showPopupWindow();
                            }else {
                                getAttachedView().showSubmitSuccess("提交成功");
                                activity.finish();
                                Log.v("address",response.getData().toString());
                            }
                        } else {
                            getAttachedView().showSubmitFailure(response.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getAttachedView().showSubmitFailure(e.getLocalizedMessage());
                        getAttachedView().dismissDialog();
                    }

                    @Override
                    public void onComplete() {
                        getAttachedView().dismissDialog();
                    }
                });

    }
}
