package scau.info.volunteertime.activity.login;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.BOConstant;
import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.UserInfo;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Toast;

public class RegisterActivity extends ActionBarActivity {

	private EditText etUserid, etPassword, etRepassword;
	private Button btRegister;

	private ScrollView scrollView;

	private String useridValue, passwordValue, repasswordValue;

	private Activity mActivity;

	private static UserBO userBO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		etUserid = (EditText) findViewById(R.id.login_username_edit);
		etPassword = (EditText) findViewById(R.id.login_password_edit);
		etRepassword = (EditText) findViewById(R.id.login_repassword_edit);

		btRegister = (Button) findViewById(R.id.register_button);

		scrollView = (ScrollView) findViewById(R.id.scrollView);

		mActivity = this;
		userBO = new UserBO();

		btRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				useridValue = etUserid.getText().toString();
				passwordValue = etPassword.getText().toString();
				repasswordValue = etRepassword.getText().toString();
				Log.d("btRegister-setOnClickListener", "useridValue = "
						+ useridValue + " passwordValue = " + passwordValue
						+ "repasswordValue = " + repasswordValue);
				if (useridValue == null || passwordValue == null
						|| repasswordValue == null
						|| useridValue.trim().equals("")
						|| passwordValue.trim().equals("")
						|| repasswordValue.trim().equals("")) {
					Toast.makeText(mActivity, "用户名密码不能为空", Toast.LENGTH_SHORT)
							.show();
				} else if (!passwordValue.trim().equals(repasswordValue.trim())) {
					Toast.makeText(mActivity, "两次密码不一致", Toast.LENGTH_SHORT)
							.show();
				} else {
					String[] str2 = { useridValue, passwordValue,
							repasswordValue };
					new CheckUserTask().execute(str2);
				}
			}
		});

	}

	/**
	 * 
	 */
	private void toNextStep() {
		Log.d("RegisterActivity", "toNextStep");
		// Create new fragment and transaction
		PlaceholderFragment newFragment = new PlaceholderFragment();
		newFragment.setUserId(useridValue);
		newFragment.setPassword(passwordValue);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.Linear, newFragment);
		// Commit the transaction
		transaction.commit();
		etUserid.setEnabled(false);
		etPassword.setEnabled(false);
		etRepassword.setEnabled(false);
		btRegister.setEnabled(false);
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				scrollView.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}, 500);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		// String userId, String password

		private Activity mContext;

		private ScrollView scrollView;

		private String userId, password;

		private EditText etName, etClass, etLongPhone, etBriefPhone, etQQ,
				etWechant;
		private RadioGroup radioGroup;
		private RadioButton rbMan, rbWoman;

		private String sexValue = "男";

		private Button btConfirm;

		public PlaceholderFragment() {
			sexValue = "男";
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
			mContext = this.getActivity();

			Log.d("bundle", "userId = " + userId + " password = " + password);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d("PlaceholderFragment", "onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);

			etName = (EditText) rootView.findViewById(R.id.register_name_edit);
			etClass = (EditText) rootView
					.findViewById(R.id.register_class_name_edit);
			etLongPhone = (EditText) rootView
					.findViewById(R.id.register_long_cell_phone_edit);
			etBriefPhone = (EditText) rootView
					.findViewById(R.id.register_brief_cell_phone_edit);
			etQQ = (EditText) rootView.findViewById(R.id.register_qq_edit);
			etWechant = (EditText) rootView
					.findViewById(R.id.register_wechant_edit);

			radioGroup = (RadioGroup) rootView
					.findViewById(R.id.register_sex_group);
			rbMan = (RadioButton) rootView
					.findViewById(R.id.register_sex1_button);
			rbWoman = (RadioButton) rootView
					.findViewById(R.id.register_sex2_button);

			rbMan.setChecked(true);
			radioGroup
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							if (checkedId == rbMan.getId()) {
								sexValue = "男";
							} else if (checkedId == rbWoman.getId()) {
								sexValue = "女";
							}
						}
					});

			btConfirm = (Button) rootView.findViewById(R.id.register_button);

			btConfirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					checkAndCommitData();
				}
			});

			return rootView;
		}

		private void checkAndCommitData() {
			Log.d("PlaceholderFragment", "checkAndCommitData");

			UserInfo userInfo = new UserInfo(0, userId, etName.getText()
					.toString(), "", etLongPhone.getText().toString(),
					etBriefPhone.getText().toString(), "", "", etClass
							.getText().toString());
			userInfo.setSex(sexValue);
			userInfo.setQq(etQQ.getText().toString());
			userInfo.setWechant(etWechant.getText().toString());

			new CommitUserDataTask(userInfo).execute(password);
		}

		/**
		 * 
		 */
		private void goToLogin() {
			Log.d("PlaceholderFragment-goToLogin", "start");

			Intent intent = new Intent(mContext, Login.class);
			startActivity(intent);
			mContext.finish();
		}

		/**
		 * @return the userId
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * @param userId
		 *            the userId to set
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password
		 *            the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		private class CommitUserDataTask extends
				AsyncTask<String, Void, Integer> {
			private ProgressDialog mDialog;
			private boolean isConnect;
			private UserInfo userInfo;

			public CommitUserDataTask(UserInfo userInfo) {
				this.userInfo = userInfo;
				mDialog = new ProgressDialog(mContext);

				mDialog.setTitle("提示");
				mDialog.setMessage("注册信息提交验证中...");
				mDialog.show();
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.os.AsyncTask#doInBackground(Params[])
			 */
			@Override
			protected Integer doInBackground(String... arg0) {
				isConnect = NetworkStateUtil.isNetworkAvailable(mContext);
				if (!isConnect) {
					Log.d("doInBackground", "hasMore or isConnect not");
					cancel(true);
					return null;
				}
				Log.d("doInBackground", "1");
				if (arg0 == null || arg0.length <= 0)
					return -1000;
				String password = arg0[0];
				return userBO.commitUserData(userInfo, password);
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
					Toast.makeText(mContext, "网络连接不正常", Toast.LENGTH_SHORT)
							.show();
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
				if (result == BOConstant.USER_HAS_EXIST) {
					Toast.makeText(mContext, "用户名已存在", Toast.LENGTH_SHORT)
							.show();
				} else if (result == BOConstant.REPASSWORD_ERROR) {
					Toast.makeText(mContext, "两次密码不一致", Toast.LENGTH_SHORT)
							.show();
				} else if (result == BOConstant.REGISTER_SUCCESS) {
					Log.d("注册", "成功");
					goToLogin();
				} else if (result == BOConstant.USER_NOT_ACTIVATED) {
					Toast.makeText(mContext, "错误不明", Toast.LENGTH_LONG).show();
				} else {
					Log.d("postFunction", "else");
					Toast.makeText(mContext, "错误不明", Toast.LENGTH_LONG).show();
				}

			}

		}

	}

	private class CheckUserTask extends AsyncTask<String[], Void, Integer> {
		private ProgressDialog mDialog;
		private boolean isConnect;

		public CheckUserTask() {
			mDialog = new ProgressDialog(mActivity);

			mDialog.setTitle("提示");
			mDialog.setMessage("用户密码验证中...");
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
			if (up.length != 3)
				return -3;
			return userBO.CheckUserRegisterResult(up[0], up[1], up[2]);
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
			if (result == BOConstant.USER_HAS_EXIST) {
				Toast.makeText(mActivity, "用户名已存在", Toast.LENGTH_SHORT).show();
			} else if (result == BOConstant.REPASSWORD_ERROR) {
				Toast.makeText(mActivity, "两次密码不一致", Toast.LENGTH_SHORT).show();
			} else if (result == BOConstant.REGISTER_SUCCESS) {
				Log.d("注册", "成功");
				toNextStep();
			} else if (result == BOConstant.USER_NOT_ACTIVATED) {
				Toast.makeText(mActivity, "错误不明", Toast.LENGTH_LONG).show();
			} else {
				Log.d("postFunction", "else");
				Toast.makeText(mActivity, "错误不明", Toast.LENGTH_LONG).show();
			}
		}

	}

}
