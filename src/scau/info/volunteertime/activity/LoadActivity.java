/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-14
 */
package scau.info.volunteertime.activity;

import scau.info.volunteertime.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * @author 蔡超敏
 * 
 */
public class LoadActivity extends Activity {

	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// 延迟3秒
	// private static final long SPLASH_DELAY_MILLIS = 1500;

	private static final String SHAREDPREFERENCES_NAME = "first_start_info";

	// 保存第一次启动软件的数据
	SharedPreferences preferences;

	/**
	 * Handler:跳转到不同界面
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_GUIDE:
				goGuide();
				break;
			case GO_HOME:
				goHome();
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);

		// toShowVideo();
		Log.d("LoadActivity-onCreate", "init");
		init();
	}

	private void init() {
		// 读取SharedPreferences中需要的数据
		// 使用SharedPreferences来记录程序的使用次数
		preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
		isFirstIn = preferences.getBoolean("isFirstStart", true);

		// 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
		if (!isFirstIn) {
			// 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
			mHandler.sendEmptyMessage(GO_HOME);
		} else {
			setGuided();
			mHandler.sendEmptyMessage(GO_GUIDE);
		}

	}

	/**
	 * 
	 * method desc：设置已经引导过了，下次启动不用再次引导
	 */
	private void setGuided() {
		Editor editor = preferences.edit();
		// 存入数据
		editor.putBoolean("isFirstStart1", false);
		// 提交修改
		editor.commit();
	}

	private void goHome() {
		Log.d("LoadActivity-goHome", "这里将执行登陆操作");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void goGuide() {
		Log.d("LoadActivity-onCreate", "goGuide");
		initializeDatabase();
		goHome();
	}

	/**
	 * 初始化数据库
	 */
	private void initializeDatabase() {
		SQLiteDatabase db = openOrCreateDatabase("volunteertimedatabase.db",
				Context.MODE_PRIVATE, null);
		// 创建results表
		db.execSQL("CREATE TABLE IF NOT EXISTS results(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR, image  VARCHAR, editor VARCHAR, publishTime VARCHAR)");
		db.close();
	}

}
