package com.ykh.yinmeng.ymykh2.common.jpush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.ykh.yinmeng.ymykh2.app.App;
import com.ykh.yinmeng.ymykh2.utils.LogUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 2019/1/3 9:26
 * Description：
 */
public class JpushClient {

    private static final String TAG = JpushClient.class.getSimpleName();
    private static JpushClient instance;

    public static JpushClient getInstance(){
        if (null == instance) {
            synchronized (JpushClient.class) {
                if (null == instance) {
                    instance = new JpushClient();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(App.getContext());
        TagAliasOperatorHelper.getInstance().init(context);
    }

    public void resumePush() {
        JPushInterface.resumePush(App.getContext());
    }

    /**
     *Jpush设置别名
     * @param uid
     */
    public void setAlias(String uid) {
        String alias = String.valueOf(uid);
        Log.i(TAG,"6666666 设置别名：uid = "+alias);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
        Log.i(TAG, "设置Jpush推送的别名alias=" + alias);
    }

    public void deleteAlias() {
        mHandler.sendEmptyMessage(MSG_DELETE_ALIAS);
    }

    public void stopPush() {
        JPushInterface.stopPush(App.getContext());
    }

    public String getRegistrationId() {
        String rid = JPushInterface.getRegistrationID(App.getContext());
        if (!rid.isEmpty()) {
            LogUtils.e(TAG, "RegId:" + rid);
        } else {
            LogUtils.e(TAG, "Get registration fail, JPush init failed!");
        }
        return rid;
    }






    /*
     * 绑定别名
     */
    private static final int GET_MEG_SUG=0;
    private static final int MSG_SET_ALIAS=1001;
    private static final int MSG_DELETE_ALIAS=1002;
//    private Handler handler=new Handler(){
//        public void handleMessage(Message msg){
//            super.handleMessage(msg);
////            SharedPreferences setid=getSharedPreferences("setid", MODE_PRIVATE);
////            Integer uid=setid.getInt("user_id", 0);
////            String uid = String.valueOf(setid.getInt("user_id",0));
//            switch (msg.what) {
//                case GET_MEG_SUG:
//                    Log.i(TAG,"接收广播，获取数据");
//                    setAlias(uid);
//                    break;
//
//                default:
//                    break;
//            }
//        }
//
//    };


    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {//专门用了一个Handler对象处理别名的注册问题
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            TagAliasBean tagAliasBean = new TagAliasBean();
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.i(TAG, "设置极光推送的别名-mHandler");
                    tagAliasBean.action = TagAliasOperatorHelper.JPUSH_ACTION_SET;
                    tagAliasBean.alias = (String) msg.obj;
                    tagAliasBean.isAliasAction = true;
                    TagAliasOperatorHelper.getInstance().handleAction(App.getContext(), 1, tagAliasBean);

//                    JPushInterface.setAliasAndTags(App.getContext(),
//                            (String) msg.obj, null, mAliasCallback);
                    break;
                case MSG_DELETE_ALIAS:
                    Log.i(TAG, "删除极光推送的别名-mHandler");
                    tagAliasBean.action = TagAliasOperatorHelper.JPUSH_ACTION_DELETE;
                    tagAliasBean.isAliasAction = true;
                    TagAliasOperatorHelper.getInstance().handleAction(App.getContext(), 1, tagAliasBean);
                    break;
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.i(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e(TAG, logs);
                    break;
            }
        }
    };
}
