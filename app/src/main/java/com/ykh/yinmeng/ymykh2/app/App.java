package com.ykh.yinmeng.ymykh2.app;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.ykh.yinmeng.ymykh2.R;
import com.ykh.yinmeng.ymykh2.common.jpush.JpushClient;


public class App extends MultiDexApplication {
	
	public static Context mContext;
	public static ImageLoader imageLoader;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = this;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		//ImageLoader.getInstance().init(config);
		imageLoader = ImageLoader.getInstance();
		MobSDK.init(this);
		JpushClient.getInstance().init(mContext);
		CrashReport.initCrashReport(getApplicationContext(), "f410fae2cf", false);
	}
	
	
	public static Context getContext() {
		return mContext;
	}
	
	public static DisplayImageOptions getDefaultOptions() {
		//设置图片加载的属性
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageForEmptyUri(R.drawable.logo);
        b.showImageOnFail(R.drawable.logo);
        b.showImageOnLoading(R.drawable.back);
        b.resetViewBeforeLoading(Boolean.TRUE);
        b.cacheOnDisk(Boolean.TRUE);
        b.cacheInMemory(Boolean.TRUE);
        b.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        return b.bitmapConfig(Bitmap.Config.RGB_565).build();
    }
	/**
	 * 未登录调用
	 * @
	 */
	public static DisplayImageOptions getDeluOptions() {
		//设置图片加载的属性
        DisplayImageOptions.Builder b = new DisplayImageOptions.Builder();
        b.showImageForEmptyUri(R.drawable.logo);
        b.showImageOnFail(R.drawable.logo);
        b.showImageOnLoading(R.drawable.back);
        b.resetViewBeforeLoading(Boolean.TRUE);
        b.cacheOnDisk(Boolean.TRUE);
        b.cacheInMemory(Boolean.TRUE);
        b.imageScaleType(ImageScaleType.EXACTLY_STRETCHED);
        return b.bitmapConfig(Bitmap.Config.RGB_565).build();
    }

	/**
	 * 设置全局的Header 和Footer
	 */
	static {
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@Override
			public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
				layout.setPrimaryColorsId(R.color.colorPrimary, R.color.text_black);//全局设置主题颜色
				return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
			}
		});
		SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
			@Override
			public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
				//指定为经典Footer，默认是 BallPulseFooter
				return new ClassicsFooter(context).setDrawableSize(20);
			}
		});
	}
}
