package com.ykh.yinmeng.ymykh2.activity.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.activity.login.LoginActivity;
import com.ykh.yinmeng.ymykh2.wideget.viewpager.DBHelper;

import java.io.IOException;
import java.util.ArrayList;



public class GuideActivity extends Activity {

	// private CopyData copydata;
	private Context Activity = GuideActivity.this;
	// 定义ViewPager对象
	private ViewPager viewPager;

	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;

	// 定义一个ArrayList来存放View
	private ArrayList<View> views;

	// 定义各个界面View对象
	private View view0,view1, view2, view3;

	// 定义开始按钮对象
	private Button startBt;

	private ImageView imageView;
	private ImageView[] imageViews;
	// 包裹小圆点的LinearLayout
//	private ViewGroup group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_guide);

		initView();

		initData();

	}

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化各个界面的布局对象
		LayoutInflater mLi = LayoutInflater.from(this);
		view0 = mLi.inflate(R.layout.guide_view00, null);
		view1 = mLi.inflate(R.layout.guide_view01, null);
		view2 = mLi.inflate(R.layout.guide_view02, null);
		view3 = mLi.inflate(R.layout.guide_view03, null);

		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// 实例化ArrayList对象
		views = new ArrayList<View>();

		// 将要分页显示的View装入数组中
		views.add(view0);
		views.add(view1);
		views.add(view2);
		views.add(view3);

		// 实例化ViewPager适配器
		vpAdapter = new ViewPagerAdapter(views);

		imageViews = new ImageView[views.size()];
//		group = (ViewGroup) findViewById(R.id.viewGroup);
//
//		for (int i = 0; i < views.size(); i++) {
//			imageView = new ImageView(GuideActivity.this);
//			imageView.setLayoutParams(new LayoutParams(20, 20));
//			imageView.setPadding(20, 0, 20, 0);
//			imageViews[i] = imageView;
//
//			if (i == 0) {
//				// 默认选中第一张图片
//				imageViews[i]
//						.setBackgroundResource(R.drawable.page_indicator_focused);
//			} else {
//				imageViews[i].setBackgroundResource(R.drawable.page_indicator);
//			}
//
//			group.addView(imageViews[i]);
//		}

		// 设置监听
		viewPager.setOnPageChangeListener(new GuidePageChangeListener());
		// 设置适配器数据
		viewPager.setAdapter(vpAdapter);

		// 实例化开始按钮
		startBt = (Button) view3.findViewById(R.id.startBtn);

		// 给开始按钮设置监听
		startBt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startbutton();
			}
		});

	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		DBHelper dbHelper = new DBHelper(this);
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// copydata=new CopyData(Activity);
		// copydata.initPaths();
		// copydata.copyAssetsFileToSdcard();
		// new AsyncTask<Void, Void, Void>(){
		// @Override
		// protected Void doInBackground(Void... params) {
		// SQLiteDatabase db = new
		// OpendbHelper(getBaseContext()).getWritableDatabase();
		// String sql =
		// "create table if not exists course (courseid integer primary key autoincrement,weekday varchar(20),section varchar(20),course varchar(30),begintime varchar(10),classroom varchar(50),week varchar(20))"
		// ;
		// System.out.println(">>>>>>"+sql) ;
		// db.execSQL(sql) ;
		// db.close() ;
		// return null;
		// }
		// }.execute();
	}

	// 指引页面更改事件监听器
	class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
//			for (int i = 0; i < imageViews.length; i++) {
//				imageViews[arg0]
//						.setBackgroundResource(R.drawable.page_indicator_focused);
//
//				if (arg0 != i) {
//					imageViews[i]
//							.setBackgroundResource(R.drawable.page_indicator);
//				}
//			}
		}
	}

	/**
	 * 相应按钮点击事件
	 */
	private void startbutton() {
		Intent intent = new Intent();
		intent.setClass(GuideActivity.this, LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}
