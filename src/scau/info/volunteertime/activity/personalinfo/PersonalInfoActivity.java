package scau.info.volunteertime.activity.personalinfo;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.UserBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.UserInfo;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class PersonalInfoActivity extends ActionBarActivity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_info2);
		mContext = this;
		new getUserInfoDataTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_info, menu);
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

	private class getUserInfoDataTask extends AsyncTask<Void, Void, UserInfo> {

		private boolean isConnect;

		@Override
		protected UserInfo doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mContext);

			Log.d("UpdateNowDataTask-doInBackground", "isConnect = "
					+ isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("UpdateNowDataTask-doInBackground", "in");

			return doInBackgroundFunction();
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(UserInfo result) {
			if (isCancelled()) {
				Log.d("Cancle", "call");
				cancelledFunction();
			} else {
				postFunction(result);
			}
			super.onPostExecute(result);
		}

		/**
		 * @param result
		 */
		private void postFunction(UserInfo result) {
			if (result != null) {
				Log.d("getUserInfoDataTask", "personalInfo success!");
				PersonalInfo personalInfo = new PersonalInfo();
				personalInfo.setUserInfo(result);
				getSupportFragmentManager().beginTransaction()
						.add(R.id.container, personalInfo).commit();
			}
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			Log.d("UpdateNowDataTask", "wrong");
		}

		/**
		 * 
		 */
		private UserInfo doInBackgroundFunction() {
			return new UserBO()
					.getUserInfo(((Ding9App) getApplicationContext())
							.getUserId());
		}
	}
}
