/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-14
 */
package scau.info.volunteertime.activity;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.login.Login;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.BOConstant;
import scau.info.volunteertime.business.UserBO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

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

	public static final String SHAREDPREFERENCES_NAME = "first_start_info";

	public static final String KEY_IS_FIRST_START = "isFirstStart";

	public static final String SHARE_ISCHECK = "ISCHECK";

	private static final int GO_LOGIN = 1002;

	private static final int GO_CHECK_LOGIN = 1003;

	public static final String SHARE_USERNAME = "USER_NAME";

	public static final String SHARE_PASSWORD = "PASSWORD";

	SharedPreferences preferences;

	private Context mContext;

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
			case GO_CHECK_LOGIN:
				goCheckLogin();
				break;
			case GO_LOGIN:
				goToLogin();
				break;
			}
			super.handleMessage(msg);
		}
	};

	private String userNameValue;

	private String passwordValue;

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
		mContext = this;
		// 进行判断
		if (!isFirstIn) {
			if (preferences.getBoolean(SHARE_ISCHECK, false))
				mHandler.sendEmptyMessageDelayed(GO_CHECK_LOGIN, 1000);
			else
				mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
		} else {
			setGuided();
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, 1000);
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
		((Ding9App) getApplicationContext()).setUserId(userNameValue);
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void goCheckLogin() {
		userNameValue = preferences.getString(SHARE_USERNAME, "").trim();
		passwordValue = preferences.getString(SHARE_PASSWORD, "").trim();

		Log.d("toLogin", ":" + userNameValue + ":" + passwordValue + ":");
		if (userNameValue == null || userNameValue.equals("")) {
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
		} else if (passwordValue == null || passwordValue.equals("")) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
		} else {
			String[] str2 = { userNameValue, passwordValue };
			new CheckLoginDataTask().execute(str2);
		}
	}

	private void goGuide() {
		Log.d("LoadActivity-onCreate", "goGuide");
		initializeDatabase();
		goToLogin();
	}

	private void goToLogin() {
		Log.d("LoadActivity-goToLogin", "start");

		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 初始化本地数据库
	 */
	private void initializeDatabase() {
		SQLiteDatabase db = openOrCreateDatabase("volunteertimedatabase.db",
				Context.MODE_PRIVATE, null);
		// 建表
		db.execSQL("CREATE TABLE IF NOT EXISTS results(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, content VARCHAR, image  VARCHAR, editor VARCHAR, publishTime BIGINT, readNum INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS activities(id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR,  content VARCHAR,  image VARCHAR, editor VARCHAR, publishTime BIGINT, endTime BIGINT, limitNum INTEGER, readNum INTEGER,  groupId INTEGER, participatorsNum INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS activity_group(id INTEGER PRIMARY KEY AUTOINCREMENT, principal_id VARCHAR, participators VARCHAR)");
		db.close();
	}

	private class CheckLoginDataTask extends AsyncTask<String[], Void, Integer> {

		@Override
		protected Integer doInBackground(String[]... params) {
			Log.d("doInBackground", "1");
			if (params == null || params.length <= 0)
				return -1000;
			String[] up = params[0];
			if (up.length != 2)
				return BOConstant.REP_VAR_PARA_ERR;
			Log.d("doInBackground", "3");
			UserBO userBO = new UserBO();
			return userBO.CheckUserLoginResult(up[0], up[1]);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (isCancelled()) {
				Log.d("Cancle", "call");
				cancelledFunction();
			} else {
				postFunction(result);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.d("onProgressUpdate", "1");
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		/**
	     * 
	     */
		private void cancelledFunction() {
			Log.d("onCancelled", "1");
		}

		/**
	     * 
	     */
		private void postFunction(Integer result) {
			Log.d("onPostExecute", result + "");
			if (result == BOConstant.PASSWORD_ERROR) {// 密码不合法
				Toast.makeText(mContext, "密码过期", Toast.LENGTH_SHORT).show();
				goToLogin();
			} else if (result == BOConstant.REGISTER_SUCCESS) {
				Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
				goHome();
			} else if (result == BOConstant.USER_NOT_EXIST) {// 用户不存在
				Toast.makeText(mContext, "用户名不存在", Toast.LENGTH_SHORT).show();
				goToLogin();
			} else if (result == BOConstant.REP_VAR_PARA_ERR) {// 帐号密码为空
				Toast.makeText(mContext, "用户名密码为空", Toast.LENGTH_SHORT).show();
				goToLogin();
			} else {
				Toast.makeText(mContext, "用户名或密码错误，请重新登录", Toast.LENGTH_LONG)
						.show();
				goToLogin();
			}
		}

	}

}
