package com.ykh.yinmeng.ymykh2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ykh.yinmeng.ymykh2.AppInterface;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.utils.ActivityManager;
import com.ykh.yinmeng.ymykh2.utils.LogUtils;
import com.ykh.yinmeng.ymykh2.utils.PermissionListener;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public abstract class BaseActivity extends FragmentActivity implements AppInterface {
	/**标示*/
	protected static final String TAG = "BaseActivity";
	
	/** 所有已存在的Activity */
    protected static ConcurrentLinkedQueue<Activity> allActivity = new ConcurrentLinkedQueue<Activity>();
    /** 同时有效的界面数量 */
    protected static final int validActivityCount = 15;
    
    /**屏幕的宽高*/
    protected int mScreenWidth;
	protected int mScreenHeight;

    /** Context对象 */
    protected Context mContext;

    private static PermissionListener mPermissionListener;
    private static final int REQUEST_CODE = 1;
    
    @Override
    protected void onCreate(Bundle arg0) {
    	super.onCreate(arg0);
    	mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//Activity队列管理，如果超出指定个数，获取并移除此队列的头（队列中时间最长的）。
    	if (allActivity.size() >= validActivityCount) {
            Activity act = allActivity.poll();
            act.finish();// 结束
        }
        allActivity.add(this);
        ActivityManager.getInstance().addActivity(this);
        printAllActivityName();
        try {//设置布局文件、初始化布局文件中的控件、初始化控件的监听、进行数据初始化。（子类重写这些方法）
        	setContentView();
    		initViews();
    		initListeners();
    		initData();
        } catch (Exception e) {
        	LogUtils.e(TAG, e.getMessage(), e);
        	e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }

    /**
	 * 设置布局文件
	 */
	public abstract void setContentView();
	/**
	 * 初始化布局文件中的控件
	 */
	public abstract void initViews();
	/**
	 * 初始化控件的监听
	 */
	public abstract void initListeners();
	/** 进行数据初始化
	  * initData
	  */
	public abstract void initData();
    
    /**
     * 获取当前屏幕宽高
     */
    public void getScreenWidthAndHeight(){
    	//获取当前屏幕宽高
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
    }
    
    /**
     * 控制台上打印 {@link BaseActivity#allActivity}
     */
    private static void printAllActivityName() {
    	for (Activity activity : allActivity) {
    		LogUtils.d(TAG, activity.getClass().getName());
        }
    }
    
    /**
     * 退出系统
     */
    public void exitAppUseDialog() {

    	MessageDialog dialog = new MessageDialog(this,
    			LOGOUT, 
    			LOGOUT, 
    			CANCEL, 
				LOGOUTINFO);
		dialog.setBtn1ClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishAll();
			}
		});
		dialog.show();
    }
    
    /**
     * 退出当前activity
     * @see Activity#finish()
     */
    public void finish() {
        try {
            super.finish();
            //软键盘隐藏
            if (null != this.getCurrentFocus() && null != this.getCurrentFocus().getWindowToken()) {
                InputMethodManager in = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                in.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            }
            // 从Activity集合中清理出已结束的Activity
            if (allActivity != null && allActivity.size() > 0 && allActivity.contains(this)) {
                allActivity.remove(this);
            }
            for (Activity a : allActivity) {
            	LogUtils.d("finish", a.getClass().getName());
            }
        } catch (Exception e) {
        	LogUtils.e(TAG, e.getMessage(), e);
        }
    }
    
    /**
     * 结束所有activity
     */
    public static void finishAll() {
        // 结束Activity
        try {
            for (Activity act : allActivity) {
                allActivity.remove(act);
                act.finish();
                printAllActivityName();
            }
        } catch (Exception e) {
        	LogUtils.e(TAG, e.getMessage(), e);
        }
    }
    
    /**
     * 判断activity是否已经结束
     * @param act
     * @return
     */
    public static boolean contains(Class<?> act) {
        // 结束Activity
        try {
            for (Activity ele : allActivity) {
                if (ele.getClass().getName().equals(act.getName())) {
                    return Boolean.TRUE;
                }
            }
        } catch (Exception e) {
        	LogUtils.e(TAG, e.getMessage(), e);
        }
        return Boolean.FALSE;
    }
    
    /**
     * 取得版本名称
     * 
     * @return
     */
    public String getVersionName() {
        if (null != getPackageInfo()) {
            return getPackageInfo().versionName;
        } else {
            return "";
        }
    }

    private PackageInfo getPackageInfo() {
        PackageManager manager = this.getPackageManager();
        try {
            return manager.getPackageInfo(this.getPackageName(), 0);
        } catch (NameNotFoundException e) {
        	LogUtils.d(TAG, e.getMessage());
        }
        return null;
    }

    public static void requestRuntimePermission(String[] permissions, PermissionListener listener) {
        Activity topStackActivity = ActivityManager.getInstance().getStackTopActivity();
        if (topStackActivity == null) {
            return;
        }
        // requestCode可以封装在内部。
        mPermissionListener = listener;
        List<String> permissionList = new ArrayList<String>();

        // 1.首先执行一个循环，将 permission s数组中所有的值取出来。在其内部执行判断。
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(topStackActivity, permission) != PackageManager
                    .PERMISSION_GRANTED) {
                // 如果当前循环的权限未被授权，则 add 进 permissionList.
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(topStackActivity, permissionList.toArray(new
                    String[permissionList.size()]), REQUEST_CODE);
        } else {
            // 3.授权，调用onGranted
            mPermissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];

                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            // 此时至少有一个权限未被授权
                            deniedPermissions.add(permission);
                        }
                    }

                    // 如果for循环成功执行完，即没有在中间被return掉，
                    // 就说明所有权限都被同意了。此时就可以执行自己的逻辑了。
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }

                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
