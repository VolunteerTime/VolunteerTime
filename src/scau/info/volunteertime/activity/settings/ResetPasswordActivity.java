package scau.info.volunteertime.activity.settings;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.BOConstant;
import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.Util;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResetPasswordActivity extends ActionBarActivity {

	private EditText etOrignPassword, etNewPassword, etNewRepassword;
	private Button btComfirm;

	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_password);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		RelativeLayout actionView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.action_bar_title_resetpasswordactivity, null);
		
		
		getSupportActionBar().setCustomView(
				actionView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		
		mActivity = this;

		etOrignPassword = (EditText) findViewById(R.id.orign_password_edit);
		etNewPassword = (EditText) findViewById(R.id.new_password_edit);
		etNewRepassword = (EditText) findViewById(R.id.new_repassword_edit);

		btComfirm = (Button) findViewById(R.id.confirm_button);

		btComfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkAndCommitPassword();
			}

		});
	}

	private void checkAndCommitPassword() {
		String orignPassword = etOrignPassword.getText().toString().trim();
		String newPassword = etNewPassword.getText().toString().trim();
		String newRepassword = etNewRepassword.getText().toString().trim();
		if (orignPassword == null || orignPassword.equals("")) {
			Toast.makeText(this, "原密码不能为空", Toast.LENGTH_SHORT).show();
		} else if (newPassword == null || newPassword.equals("")) {
			Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
		} else if (!newRepassword.equals(newRepassword)) {
			Toast.makeText(this, "修改密码不一致", Toast.LENGTH_SHORT).show();
		} else {
			String userId = ((Ding9App) getApplicationContext()).getUserId();
			new ChangePasswordTask(userId,
					Util.getMD5Str(orignPassword.trim()),
					Util.getMD5Str(newPassword.trim())).execute();
		}
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

	private class ChangePasswordTask extends AsyncTask<Void, Void, Integer> {
		private ProgressDialog mDialog;
		private boolean isConnect;

		private String userId, orignPassword, newPassword;

		public ChangePasswordTask(String userId, String orignPassword,
				String newPassword) {
			mDialog = new ProgressDialog(mActivity);

			mDialog.setTitle("提示");
			mDialog.setMessage("用户密码修改中...");
			mDialog.show();

			this.userId = userId;
			this.orignPassword = orignPassword;
			this.newPassword = newPassword;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Integer doInBackground(Void... arg0) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mActivity);
			if (!isConnect) {
				Log.d("doInBackground", "hasMore or isConnect not");
				cancel(true);
				return null;
			}
			Log.d("doInBackground", "1");

			return new UserBO().changePassword(userId, orignPassword,
					newPassword);
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
			if (result == BOConstant.CHANGE_SUCCESS) {
				Toast.makeText(mActivity, "密码修改成功", Toast.LENGTH_LONG).show();
				mActivity.finish();
			} else if (result == BOConstant.PASSWORD_ERROR) {
				Toast.makeText(mActivity, "原密码错误", Toast.LENGTH_LONG).show();
			} else {
				Log.d("postFunction", "else");
				Toast.makeText(mActivity, "错误不明", Toast.LENGTH_LONG).show();
			}
		}

	}

}
