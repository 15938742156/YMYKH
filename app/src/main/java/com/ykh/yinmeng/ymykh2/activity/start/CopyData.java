package com.ykh.yinmeng.ymykh2.activity.start;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 首次启动时copy数据类
 * @author gis
 *
 */

public class CopyData {
	private Context Activity;
	public static String PATH = android.os.Environment
			.getExternalStorageDirectory() + "/Tutor_stu/Map";
	
	public CopyData(Context Activity0) {
		Activity = Activity0;
	}

	/**
	 * 创建几个必须的目录，设置运行环境
	 */
	public void initPaths() {
		createDir(PATH);
	}

	public void createDir(String foldername) {
		File dir = new File(foldername);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	/**
	 * 从工程资源里面复制相关文件到存储卡上
	 */
	public void copyAssetsFileToSdcard() {
		copyFile("ChinaMap.jpg", "Map");
	}

	public void copyFile(String filename, String desFolder) {
		InputStream inputFile = null;
		OutputStream outputFile = null;
		try {
			String desFileName = PATH + "/" + filename;
			File desFile = new File(desFileName);
			if (!desFile.exists()) {
				inputFile = Activity.getAssets().open(filename);
				outputFile = new FileOutputStream(desFile);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputFile.read(buffer)) > 0) {
					outputFile.write(buffer, 0, length);
				}
				outputFile.flush();
				outputFile.close();
				inputFile.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
