package com.ykh.yinmeng.ymykh2.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import com.ykh.yinmeng.ymykh2.app.App;
import com.zhy.base.fileprovider.FileProvider7;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created At 2018/12/20 0:30.
 *
 * @author larry
 */
public class ImageUtil {
    private static final String IMAGE_SUFFIX = ".jpg";

    /**
     * 头像拍照
     */
    public static final int HEADER = 100;

    /**
     * 从相册中选取图片作为头像
     */
    public static final int CHOOSE_PHOTO = 101;

    /**
     * 人证识别
     */
    public static final int FACE = 102;

    private ImageUtil() {

    }

    public interface OnCompressBitmapListener {
        void onCompressBitmap(Bitmap bitmap);
    }

    public interface OnCompressFileListener {
        void onCompressFile(String filePath);
    }

    public static Uri takePhotoForFace(Activity activity, int index) {
        if (!isSDAvail()) {
            return null;
        }
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(App
                .getContext().getExternalCacheDir(), index + IMAGE_SUFFIX);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = FileProvider7.getUriForFile(activity, outputImage);
        // 启动相机程序
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
//        activity.startActivityForResult(intent, FACE);
        activity.startActivityForResult(intent, index);
        return imageUri;
    }

    public static Uri takePhotoForHeader(Activity activity) {
        if (!isSDAvail()) {
            return null;
        }
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(App
                .getContext().getExternalCacheDir(), IMAGE_SUFFIX);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = FileProvider7.getUriForFile(activity, outputImage);
//        if (Build.VERSION.SDK_INT < 24) {
//            imageUri = Uri.fromFile(outputImage);
//        } else {
//            imageUri = FileProvider.getUriForFile(activity,
//                    "com.doormaster.topkeeper.fileprovider", outputImage);
//        }
        // 启动相机程序
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        activity.startActivityForResult(intent, HEADER);
        return imageUri;
    }

    private static Tiny.FileCompressOptions getOption() {
        Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
        options.outfile = App
                .getContext().getExternalCacheDir().getAbsolutePath();
        LogUtils.d("指定的路径是：" + options.outfile);
        options.overrideSource = true;
        return options;
    }

    /*public static void compress2Bitmap(Uri imageUri, final OnCompressBitmapListener listener) {
        Tiny.getInstance()
                .source(imageUri)
                .asBitmap()
                .withOptions(getOption())
                .compress(new BitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                        if (!isSuccess) {
                            LogUtils.e("压缩失败");
                            return;
                        }
                        // return the compressed bitmap object
                        if (listener != null) {
                            listener.onCompressBitmap(bitmap);
                        }
                    }
                });
    }*/

    /*public static void compress2Bitmap(String imagePath, final OnCompressBitmapListener listener) {
        LogUtils.d("要压缩的图片路径：" + imagePath);
        Tiny.getInstance()
                .source(imagePath)
                .asBitmap()
                .withOptions(getOption())
                .compress(new BitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, Throwable t) {
                        if (!isSuccess) {
                            LogUtils.e("压缩失败");
                            return;
                        }
                        LogUtils.d("压缩成功");
                        LogUtils.d("大小： " + (bitmap.getRowBytes() * bitmap.getHeight()) / 1024);
                        // return the compressed bitmap object
                        if (listener != null) {
                            listener.onCompressBitmap(bitmap);
                        }
                    }
                });
    }*/

    public static void compress2File(String imagePath, final OnCompressFileListener listener) {
        Tiny.getInstance()
                .source(imagePath)
                .asFile()
                .withOptions(getOption())
                .compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        if (TextUtils.isEmpty(outfile)) {
                            LogUtils.e("压缩图片失败");
                            return;
                        }
                        if (listener != null) {
                            listener.onCompressFile(outfile);
                        }
//                        if (LOG_DEBUG) {
//                            File file = new File(outfile);
//                            long fileSize = file.length() / 1024;
//                            LogUtils.d("实际的压缩文件路径：" + outfile + "，大小：" + fileSize + "KB");
//                        }
                    }
                });
    }

    public static void compress2File(Bitmap bitmap, final OnCompressFileListener listener) {
        Tiny.getInstance()
                .source(bitmap)
                .asFile()
                .withOptions(getOption())
                .compress(new FileCallback() {
                    @Override
                    public void callback(boolean isSuccess, String outfile, Throwable t) {
                        if (TextUtils.isEmpty(outfile)) {
                            LogUtils.e("压缩图片失败");
                            return;
                        }
                        if (listener != null) {
                            listener.onCompressFile(outfile);
                        }
//                        if (LOG_DEBUG) {
//                            File file = new File(outfile);
//                            long fileSize = file.length() / 1024;
//                            LogUtils.d("实际的压缩文件路径：" + outfile + "，大小：" + fileSize + "KB");
//                        }
                    }
                });
    }


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    @TargetApi(19)
    public static String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(App.getContext(), uri)) {
            //  如果是 document  类型的 Uri ，则通过 document id  处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; //  解析出数字格式的 id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.
                    getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //  如果是 content  类型的 Uri ，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //  如果是 file  类型的 Uri ，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    public static String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        return imagePath;
    }

    private static String getImagePath(Uri uri, String selection) {
        String path = null;
        //  通过 Uri 和 和 selection  来获取真实的图片路径
        Cursor cursor = App.getContext().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.
                        Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public static File getTempHeaderFile() {
        if (!isSDAvail()) {
            return null;
        }
        File tempFile = new File(App.getContext().getExternalCacheDir(), IMAGE_SUFFIX);
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static File getTempFaceFile(int index) {
        if (!isSDAvail()) {
            return null;
        }
        File tempFile = new File(App.getContext().getExternalCacheDir(), index + IMAGE_SUFFIX);
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static File getImageByPath(String path) {
        if (!isSDAvail()) {
            return null;
        }
        File tempFile = new File(path);
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    public static String getTempFacePath() {
        return App.getContext().getExternalCacheDir() +
                File.separator + System.currentTimeMillis() + IMAGE_SUFFIX;
    }

    public static File getFaceDirectory() {
        return App.getContext().getExternalCacheDir();
    }

    public static boolean isSDAvail() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }
    /**
     * 将图片剪裁为圆形
     */
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }
}
