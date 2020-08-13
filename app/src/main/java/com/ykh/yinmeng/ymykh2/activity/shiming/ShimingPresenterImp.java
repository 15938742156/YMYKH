package com.ykh.yinmeng.ymykh2.activity.shiming;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.baidu.ocr.ui.camera.CameraNativeHelper;
import com.baidu.ocr.ui.camera.CameraView;
import com.baidu.ocr.ui.util.ImageUtil;
import com.ykh.yinmeng.ymykh2.activity.base.BasePresenter;
import com.ykh.yinmeng.ymykh2.activity.shimingrenzheng.ShimingrenzhengActivity;
import com.ykh.yinmeng.ymykh2.activity.xieyi.XieyiShimingActivity;
import com.ykh.yinmeng.ymykh2.activity.xieyi.XieyiYinsiActivity;
import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.bean.IDCardBean;
import com.ykh.yinmeng.ymykh2.model.LzyResponse;
import com.ykh.yinmeng.ymykh2.network.ServerApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShimingPresenterImp<V extends ShimingMVPView> extends BasePresenter<V> implements ShimingPresenter<V> {

    private static final int REQUEST_CODE_PICK_IMAGE_FRONT = 201;
    private static final int REQUEST_CODE_PICK_IMAGE_BACK = 202;
    private static final int REQUEST_CODE_CAMERA = 102;

    private boolean hasGotToken = false;
    IDCardBean resultZM = null;
    IDCardBean resultFM = null;
    String pathZm = null;
    String pathFm = null;
    public ShimingPresenterImp() {
        super();
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
        initAccessToken();

        CameraNativeHelper.init(App.getContext(), OCR.getInstance(App.getContext()).getLicense(),
                new CameraNativeHelper.CameraNativeInitCallback() {
                    @Override
                    public void onError(int errorCode, Throwable e) {
                        String msg;
                        switch (errorCode) {
                            case CameraView.NATIVE_SOLOAD_FAIL:
                                msg = "加载so失败，请确保apk中存在ui部分的so";
                                break;
                            case CameraView.NATIVE_AUTH_FAIL:
                                msg = "授权本地质量控制token获取失败";
                                break;
                            case CameraView.NATIVE_INIT_FAIL:
                                msg = "本地质量控制";
                                break;
                            default:
                                msg = String.valueOf(errorCode);
                        }
                        getAttachedView().showAlert("", "本地质量控制初始化错误，错误原因： " + msg);
                    }
                });
    }

    @Override
    public void onDetach() {
        // 释放本地质量控制模型
        CameraNativeHelper.release();
        super.onDetach();
    }

    @Override
    public void onToggleZhengMian(Activity activity) {
        if (!checkTokenStatus()) {
            return;
        }
        selectZmNative(activity);
    }

    @Override
    public void onToggleFanMian(Activity activity) {
        if (!checkTokenStatus()) {
            return;
        }
        selectFmNative(activity);
    }

    @Override
    public void onToggleNext(Activity activity) {

        if (resultZM == null) {
            getAttachedView().showAlert("", "请选择身份证正面照片");
            return;
        }
        if (resultFM == null) {
            getAttachedView().showAlert("", "请选择身份证背面照片");
            return;
        }
        if(resultZM.getName() == null && resultFM.getName().isEmpty()){
            if(resultZM.getGender() == null && resultFM.getGender().isEmpty()){
                if(resultZM.getEthnic() == null && resultFM.getEthnic().isEmpty()){
                    if(resultZM.getBirthday() == null && resultFM.getBirthday().isEmpty()){
                        if(resultZM.getIdNumber() == null && resultFM.getIdNumber().isEmpty()){
                            if(resultZM.getAddress() == null && resultFM.getAddress().isEmpty()){
                                getAttachedView().showAlert("", "身份证正面模糊，请重新识别");
                            }
                        }
                    }
                }
            }
        }
        if(resultZM.getSignDate() == null&& resultFM.getSignDate().isEmpty()){
            if(resultZM.getExpiryDate() == null && resultFM.getExpiryDate().isEmpty()){
                if(resultZM.getIssueAuthority() == null && resultFM.getIssueAuthority().isEmpty()){
                    getAttachedView().showAlert("", "身份证反面模糊，请重新识别");
                }
            }
        }else {
            Intent intent= new Intent(App.getContext(), ShimingrenzhengActivity.class);

            Bundle bundle=new Bundle();
            bundle.putParcelable("resultZM",resultZM);
            bundle.putParcelable("resultFM",resultFM);
            bundle.putString("pathZm", pathZm);
            bundle.putString("pathFm", pathFm);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);
            activity.finish();

        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.putExtras(bundle);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            App.getContext().startActivity(intent);
//            activity.finish();
//        }else {
//            intent.putExtras(bundle);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            App.getContext().startActivity(intent);
//            activity.finish();
//        }

    }

    @Override
    public void onXieYiShiming(Activity activity) {
        Intent intent= new Intent(App.getContext(), XieyiShimingActivity.class);
        App.getContext().startActivity(intent);
    }

    @Override
    public void onXieYiYinsi(Activity activity) {
        Intent intent= new Intent(App.getContext(), XieyiYinsiActivity.class);
        App.getContext().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE_FRONT && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE_BACK && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            String filePath = getRealPathFromURI(uri);
            recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
        }

        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath;
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        filePath = getSaveFileZM(App.getContext()).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        filePath = getSaveFileFM(App.getContext()).getAbsolutePath();
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private void selectZm(Activity activity) {
        Intent intent = new Intent(App.getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFileZM(App.getContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void selectZmNative(Activity activity) {
        Intent intent = new Intent(App.getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFileZM(App.getContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void selectFm(Activity activity) {
        Intent intent = new Intent(App.getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFileFM(App.getContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private void selectFmNative(Activity activity) {
        Intent intent = new Intent(App.getContext(), CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                getSaveFileFM(App.getContext()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
                true);
        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        activity.startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 以license文件方式初始化
     */
    private void initAccessToken() {
        getAttachedView().showDialog();
        OCR.getInstance(App.getContext()).initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
                getAttachedView().dismissDialog();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                getAttachedView().showAlert("licence方式获取token失败", error.getMessage());
                getAttachedView().dismissDialog();
            }
        }, App.getContext());
    }

    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(App.getContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    private void recIDCard(String idCardSide, final String filePath) {
        Log.i("recIDCard", "filePath = "+filePath);
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(App.getContext()).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                    Bitmap bitmap = null;
                    if (TextUtils.isEmpty(result.getIdCardSide())) {
                    }else {
                        bitmap = ImageUtil.decodeImage(filePath);
                    }
                    if (result.getIdCardSide().equals("front")) {//正面
                        if (bitmap != null
                                || result.getName() != null
                                || result.getGender() != null
                                || result.getEthnic() != null
                                || result.getBirthday() != null
                                || result.getIdNumber() != null
                                || result.getAddress() != null) {
                            resultZM = new IDCardBean(result);
                            pathZm = filePath;
                            uploadImg(bitmap, resultZM);
                            getAttachedView().showZhengMian(bitmap);
                        } else {
                            getAttachedView().showAlert("", "身份证正面识别失败，请重试！");
                        }
                    } else {//背面
                        if (bitmap != null
                                || result.getSignDate() != null
                                || result.getExpiryDate() != null
                                || result.getIssueAuthority() != null) {
                            resultFM = new IDCardBean(result);
                            pathFm = filePath;
                            uploadImg(bitmap, resultFM);
                            getAttachedView().showFanMian(bitmap);
                        }else {
                            getAttachedView().showAlert("", "身份证背面识别失败，请重试！");
                        }
                    }
//                    getAttachedView().showAlert("", result.toString());
                }
            }

            @Override
            public void onError(OCRError error) {
                getAttachedView().showAlert("", error.getMessage());
            }
        });
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = App.getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static File getSaveFileZM(Context context) {
        File file = new File(context.getFilesDir(), "zm.jpg");
        return file;
    }
    public static File getSaveFileFM(Context context) {
        File file = new File(context.getFilesDir(), "fm.jpg");
        return file;
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }
    }

    private void uploadImg(Bitmap bitmap, final IDCardBean result){
        ServerApi.<LzyResponse>uploadBase64Images(bitmap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) {
                        Log.i("Shiming", "上传图片");
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
                            Log.i("Shiming", "上传图片成功");
                            String data = response.getData().toString();
                            result.setUrl(data);
                            Log.e("Shiming", "data:path:"+data);
                        } else {
                            //上传失败
                            Log.i("Shiming", "上传图片失败 code:" + response.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
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
