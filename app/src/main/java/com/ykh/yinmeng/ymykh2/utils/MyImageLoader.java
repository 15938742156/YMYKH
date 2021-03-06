package com.ykh.yinmeng.ymykh2.utils;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.ykh.yinmeng.ymykh2.R;

import java.io.File;


/**
 * 
 * @author wangsj
 * 
 */
public class MyImageLoader {

	private MyImageLoader() {
		// this.activity=activity;
	}

	/**
	 * 
	 */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");

		/*
		 * ImageLoaderConfiguration config = new
		 * ImageLoaderConfiguration.Builder( activity.getApplicationContext())
		 * .memoryCacheExtraOptions(480, 800) // max width, max height
		 * .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) //
		 * Can slow ImageLoader, use it carefully (Better don't use // it)/ //
		 * .taskExecutor(null) // .taskExecutorForCachedImages(null)
		 * .threadPoolSize(3) // .threadPriority(Thread.NORM_PRIORITY - 2)
		 * .denyCacheImageMultipleSizesInMemory() .memoryCache(new
		 * UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your
		 * own memory cache // implementation/ .memoryCacheSize(2 * 1024 * 1024)
		 * .discCacheSize(50 * 1024 * 1024) .discCacheFileNameGenerator(new
		 * Md5FileNameGenerator()) //
		 * .tasksProcessingOrder(QueueProcessingType.LIFO)
		 * .discCacheFileCount(100) // .discCache(new
		 * UnlimitedDiscCache(cacheDir))//
		 * .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
		 * .imageDownloader( new
		 * BaseImageDownloader(activity.getApplicationContext(), 5 * 1000, 30 *
		 * 1000)) // connectTimeout // (5 // s), // readTimeout // (30 // s)
		 * .writeDebugLogs() // Remove for release app .build();// // Initialize
		 * ImageLoader with configuration.
		 * if(!ImageLoader.getInstance().isInited())
		 * ImageLoader.getInstance().init(config);// //
		 * ImageLoader.getInstance()
		 * .displayImage("http://192.168.1.100:8080/videonews/images/image1.jpg"
		 * , // imageView);
		 */

		// // 使用DisplayImageOptions.Builder()创建DisplayImageOptions
		// DisplayImageOptions options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
		// .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		// .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
		// .build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
//				.discCacheExtraOptions(480, 480800, CompressFormat.JPEG, 75, null)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
																				// (5
																				// s),
																				// readTimeout
																				// (30
																				// s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置

	}

	public static void destory() {
		ImageLoader.getInstance().destroy();
	}

	public static DisplayImageOptions getOptions() {
		DisplayImageOptions options; // DisplayImageOptions是用于设置图片显示的类
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.logo) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.logo) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.logo) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对
		return options;
	}
}
