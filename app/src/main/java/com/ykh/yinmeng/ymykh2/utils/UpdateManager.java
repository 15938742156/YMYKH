package com.ykh.yinmeng.ymykh2.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.core.http.HttpHeaders;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.google.gson.reflect.TypeToken;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.model.VersionResponse;
import com.ykh.yinmeng.ymykh2.network.Convert;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.UpdateBaseDialog;

import java.lang.reflect.Type;


/**
 * 2018/12/19 10:33
 * Description：
 */
public class UpdateManager {

    public UpdateManager(){

    }
    private DownloadBuilder builder;

    public void sendRequest(final Activity activity) {
        builder = AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setRequestUrl(Urls.URL_INDEX_VERSION)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        return crateUIData(activity,result);
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
//                        ToastUtils.showToast(activity, "request failed", Toast.LENGTH_SHORT);
                    }
                });


        //是否强制更新
//        if (isForce) {
            builder.setForceUpdateListener(new ForceUpdateListener() {
                @Override
                public void onShouldForceUpdate() {
                    forceUpdate(activity);
                }
            });
//        }

        //是否静默下载更新
//        if (silentDownload) {
//            builder.setSilentDownload(true);
//        }

        //是否强制下载
//        if (forceDownload) {
//            builder.setForceRedownload(true);
//        }

        //是否显示下载进度
//        if (!showDownloading) {
//            builder.setShowDownloadingDialog(false);
//        }

        //是否显示通知
//        if (!showNotification) {
//            builder.setShowNotification(false);
//        }

        //自定义通知栏
//        if (customNotification) {
//            builder.setNotificationBuilder(createCustomNotification());
//        }

        //是否显示下载失败
//        if (!showDownloadFailed) {
//            builder.setShowDownloadFailDialog(false);
//        }

        //是否静默下载并安装
//        if (silentDownloadCheckBoxAndInstall) {
//            builder.setDirectDownload(true);
//            builder.setShowNotification(false);
//            builder.setShowDownloadingDialog(false);
//            builder.setShowDownloadFailDialog(false);
//        }

        //更新界面选择
        switch (3) {
            case 1:
                break;
            case 2:
                builder.setCustomVersionDialogListener(createCustomDialogOne());
                break;
            case 3:
                builder.setCustomVersionDialogListener(createCustomDialogTwo());
                break;
        }

        //下载进度界面选择
        switch (1) {
            case 1:
                break;
            case 2:
                builder.setCustomDownloadingDialogListener(createCustomDownloadingDialog(activity));
                break;
        }
        //下载失败界面选择
        switch (1) {
            case 1:
                break;
            case 2:
                builder.setCustomDownloadFailedListener(createCustomDownloadFailedDialog());
                break;
        }

        //自定义下载路径
        builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/Download/");
        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {
                ToastUtils.showToast(activity, "取消更新", Toast.LENGTH_SHORT);
            }
        });
        builder.executeMission(activity);
    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadFailedListener createCustomDownloadFailedDialog() {
        return new CustomDownloadFailedListener() {
            @Override
            public Dialog getCustomDownloadFailed(Context context, UIData versionBundle) {
                return new UpdateBaseDialog(context, R.style.BaseDialog, R.layout.custom_download_failed_dialog);
            }
        };
    }

    /**
     * 强制更新操作
     * 通常关闭整个activity所有界面，这里方便测试直接关闭当前activity
     */
    private void forceUpdate(Activity activity) {
        ToastUtils.showToast(activity, "必须更新才能继续使用", Toast.LENGTH_SHORT);
        activity.finish();
    }

    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadingDialogListener createCustomDownloadingDialog(final Activity activity) {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                UpdateBaseDialog baseDialog = new UpdateBaseDialog(context, R.style.BaseDialog, R.layout.custom_download_layout);
                return baseDialog;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                TextView tvProgress = dialog.findViewById(R.id.tv_progress);
                ProgressBar progressBar = dialog.findViewById(R.id.pb);
                progressBar.setProgress(progress);
                tvProgress.setText(activity.getString(R.string.versionchecklib_progress, progress));
            }
        };
    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     * 自定义的dialog UI参数展示，使用versionBundle
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogOne() {
        return new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                UpdateBaseDialog baseDialog = new UpdateBaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_one_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                return baseDialog;
            }
        };
    }


    private CustomVersionDialogListener createCustomDialogTwo() {
        return new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                UpdateBaseDialog baseDialog = new UpdateBaseDialog(context, R.style.BaseDialog, R.layout.custom_dialog_two_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                baseDialog.setCanceledOnTouchOutside(false);
                return baseDialog;
            }
        };
    }

    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.drawable.logo)
                .setTicker("更新")
                .setContentTitle("更新")
                .setContentText("更新");
    }

    /**
     * @return
     * @important 使用请求版本功能，可以在这里设置downloadUrl
     * 这里可以构造UI需要显示的数据
     * UIData 内部是一个Bundle
     */
    private UIData crateUIData(Activity activity,String result) {
        Log.i("udong", "onRequestVersionSuccess :" + result);
        try {
            Type type = new TypeToken<VersionResponse>() {}.getType();
            VersionResponse dataBean = Convert.fromJson(result, type);
            if (dataBean.getCode() == 1) {
                String versionName = AppUtils.getVersionName(activity);
                String serverVersionName = dataBean.getData().getVersion();
                Log.e("udong", "onRequestVersionSuccess :localversionName :" + versionName+",serverVersionName: "+serverVersionName);
                int ret = compareVersion(serverVersionName, versionName);
                if (ret > 0) {
                    //服务器版本号大于本地版本号
                    //get the data response from server,parse,get the `downloadUlr` and some other ui date
                    UIData uiData = UIData
                            .create()
                            .setDownloadUrl(Urls.URL_VERSION_DOWNLOAD)
                            .setTitle("更新")
                            .setContent("检测到新版本，请更新");
                    //return null if you dont want to update application
                    uiData.getVersionBundle().putString("key", "your value");
                    return uiData;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        Log.d("udong", "version1Array=="+version1Array.length);
        Log.d("udong", "version2Array=="+version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        Log.d("udong", "verTag2=2222="+version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    public void sendRequestV1(final Activity activity) {
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        AllenVersionChecker
                .getInstance()
                .requestVersion()
                .setHttpHeaders(headers)
                .setRequestMethod(HttpRequestMethod.POSTJSON)
                .setRequestParams(params)
                .setRequestUrl(Urls.URL_INDEX_VERSION)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        return crateUIData(activity,result);
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {

                    }
                })
                .executeMission(activity);
    }

    public static void cancelAllMission(Activity activity) {
        AllenVersionChecker.getInstance().cancelAllMission(activity);
    }
}
