/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.login;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.MainActivity;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.BOConstant;
import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author 蔡超敏
 * 
 */
public class Login extends ActionBarActivity {

	private EditText etUserid, etPassword;
	private Button btLogin, btRegister;

	private String useridValue, passwordValue;

	private Activity mActivity;

	private UserBO userBO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		etUserid = (EditText) findViewById(R.id.login_username_edit);
		etPassword = (EditText) findViewById(R.id.login_password_edit);

		btLogin = (Button) findViewById(R.id.signin_button);
		btRegister = (Button) findViewById(R.id.register_button);

		mActivity = this;
		userBO = new UserBO();

		btLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toLogin();
			}
		});
		btRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toRegister();
			}
		});
	}

	/**
	 * 
	 */
	private void toNextActivity() {
		Log.d("login", "toNextActivity1");
		((Ding9App) getApplicationContext()).setUserId(useridValue);

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();

	}

	/**
	 * 
	 */
	private void toLogin() {
		useridValue = etUserid.getText().toString().trim();
		passwordValue = etPassword.getText().toString().trim();
		Log.d("toLogin", ":" + useridValue + ":" + passwordValue);
		if (useridValue == null || useridValue.equals("")) {
			Toast.makeText(mActivity, "用户名不能为空", Toast.LENGTH_LONG).show();
		} else if (passwordValue == null || passwordValue.equals("")) {
			Toast.makeText(mActivity, "密码不能为空", Toast.LENGTH_LONG).show();
		} else {
			String[] str2 = { useridValue, passwordValue };
			new CheckDataTask().execute(str2);
		}
	}

	private void toRegister() {
		Log.d("login", "toRegister");
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class CheckDataTask extends AsyncTask<String[], Void, Integer> {
		private ProgressDialog mDialog;
		private boolean isConnect;

		public CheckDataTask() {
			mDialog = new ProgressDialog(mActivity);

			mDialog.setTitle("提示");
			mDialog.setMessage("正在登陆验证中...");
			mDialog.show();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(String[]... arg0) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mActivity);
			if (!isConnect) {
				Log.d("doInBackground", "hasMore or isConnect not");
				cancel(true);
				return null;
			}
			Log.d("doInBackground", "1");
			if (arg0 == null || arg0.length <= 0)
				return -1000;
			String[] up = arg0[0];
			if (up.length != 2)
				return -3;
			return userBO.CheckUserLoginResult(up[0], up[1]);
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
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		private void cancelledFunction() {
			if (!isConnect) {
				Toast.makeText(mActivity, "网络连接不正常", Toast.LENGTH_SHORT).show();
			}
			Log.d("onCancelled", "1");
			mDialog.dismiss();
		}

		/**
		     * 
		     */
		private void postFunction(Integer result) {
			mDialog.dismiss();
			Log.d("onPostExecute", result + "");
			if (result == BOConstant.USER_NOT_EXIST) {
				Toast.makeText(mActivity, "用户名不存在", Toast.LENGTH_SHORT).show();
			} else if (result == BOConstant.PASSWORD_ERROR) {
				Toast.makeText(mActivity, "密码错误", Toast.LENGTH_SHORT).show();
			} else if (result == BOConstant.USER_REGISTER_SUCCESS) {
				Log.d("登陆", "成功");
				toNextActivity();
			} else if (result == BOConstant.USER_NOT_ACTIVATED) {
				Toast.makeText(mActivity, "错误不明", Toast.LENGTH_LONG).show();
			} else {
				Log.d("postFunction", "else");
				Toast.makeText(mActivity, "错误不明", Toast.LENGTH_LONG).show();
			}
		}

	}
}
