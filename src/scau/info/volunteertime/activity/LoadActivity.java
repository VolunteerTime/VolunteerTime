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
import android.view.Window;

/**
 * @author 蔡超敏
 * 
 */
public class LoadActivity extends Activity {

	boolean isFirstIn = false;

	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE = 1001;
	// �ӳ�3��
	// private static final long SPLASH_DELAY_MILLIS = 1500;

	private static final String SHAREDPREFERENCES_NAME = "first_start_info";

	private static final String KEY_IS_FIRST_START = "isFirstStart";

	SharedPreferences preferences;

	/**
	 * Handler处理器
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
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_load);

		// toShowVideo();
		Log.d("LoadActivity-onCreate", "init");
		init();
	}

	private void init() {
		preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);

		// 检测是否第一次启动软件
		isFirstIn = preferences.getBoolean(KEY_IS_FIRST_START, true);

		// 进行判断
		if (!isFirstIn) {
			mHandler.sendEmptyMessageDelayed(GO_HOME,1000);
		} else {
			setGuided();
			mHandler.sendEmptyMessageDelayed(GO_GUIDE,1000);
		}

	}

	/**
	 * 
	 * 
	 */
	private void setGuided() {
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_IS_FIRST_START, false);
		editor.commit();
	}

	private void goHome() {
		Log.d("LoadActivity-goHome", "start");

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
	 * 初始化本地数据库
	 */
	private void initializeDatabase() {
		SQLiteDatabase db = openOrCreateDatabase("volunteertimedatabase.db",
				Context.MODE_PRIVATE, null);
		// 建表
		db.execSQL("CREATE TABLE IF NOT EXISTS results(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR, image  VARCHAR, editor VARCHAR, publishTime BIGINT)");
		db.close();
	}

}
