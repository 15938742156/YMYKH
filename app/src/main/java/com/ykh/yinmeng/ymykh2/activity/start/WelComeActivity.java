package com.ykh.yinmeng.ymykh2.activity.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.utils.MyImageLoader;


/**
 * 欢迎页
 * @author wangsj QQ:1521192337
 * 
 * @since 2016-2-22 下午12:09:50
 * 
 * @version
 */
public class WelComeActivity extends Activity implements Runnable {

	private boolean isFirstUse;
	ImageView iv_welcome_txt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);

		iv_welcome_txt=(ImageView) findViewById(R.id.iv_welcome_txt);
		// ImageLoader初始化
		MyImageLoader.initImageLoader(WelComeActivity.this);
				
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			/**
			 * 延迟1.5秒时间
			 */
//			Thread.sleep(1000);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
//					iv_welcome_txt.setImageResource(R.drawable.welcome_bg_txt);
					iv_welcome_txt.setVisibility(View.GONE);
					AlphaAnimation aa=new AlphaAnimation(0.0f, 1.0f);
					aa.setDuration(500);
					iv_welcome_txt.startAnimation(aa);
//					iv_welcome_txt.setVisibility(View.VISIBLE);
				}
			});
			Thread.sleep(1500);

// 			读取SharedPreferences中需要的数据
//			SharedPreferences preferences = getSharedPreferences(
//					"carownerdata", MODE_WORLD_READABLE);
//
//			isFirstUse = preferences.getBoolean("isFirstUse", true);
			
//			isFirstUse = (Boolean) SharedPreferencesUtil.getData(WelComeActivity.this, "isFirstUse", true);

			/**
			 * 如果用户不是第一次使用则直接调转到显示界面,否则调转到引导界面
			 */
			if (isFirstUse) {
				startActivity(new Intent(WelComeActivity.this, GuideActivity.class));
			} else {
				startActivity(new Intent(WelComeActivity.this, LoginActivity.class));
			}
			finish();
//			SharedPreferencesUtil.saveData(WelComeActivity.this, "isFirstUse", false);
//			// 实例化Editor对象
//			Editor editor = preferences.edit();
//			// 存入数据
//			editor.putBoolean("isFirstUse", false);
//			//初始化未登录
//			editor.putBoolean("isLogin", false);
//			// 提交修改
//			editor.commit();
		} catch (InterruptedException e) {

		}
	}
}
