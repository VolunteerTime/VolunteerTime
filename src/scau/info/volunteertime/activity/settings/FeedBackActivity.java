package scau.info.volunteertime.activity.settings;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.LoadActivity;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.FeedBackBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.Toast;
import cn.trinea.android.common.util.ToastUtils;

public class FeedBackActivity extends ActionBarActivity {

	private Activity mContext;
	private Ding9App ding9App;
	private EditText feedBackEdit;
	public String userId;
	public String content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_back);
		mContext = this;
		ding9App = (Ding9App) getApplicationContext();
		feedBackEdit = (EditText) findViewById(R.id.feed_back_content);
		getSupportActionBar().setTitle("意见反馈");
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		userId = ding9App.getUserId();
	}

	/**
	 * 发表反馈任务
	 * 
	 */
	private class PostFeedBackTask extends AsyncTask<Void, Void, Integer> {

		private FeedBackBO feedBackBO;// 反馈BO
		private boolean isConnect;// 是否连接网络
		private boolean isLogin;// 是否登陆
		private int result;
		private ProgressDialog pd;

		public PostFeedBackTask() {

			feedBackBO = new FeedBackBO();
			this.result = 0;
		}

		@Override
		protected Integer doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mContext);// 获取连接状况
			SharedPreferences sp = mContext.getSharedPreferences(
					LoadActivity.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
			Log.v("feedback", "1");
			isLogin = (userId != null && !userId.equals(""));
			Log.v("feedback", "2");
			if (!isConnect)// 无网络则取消任务
			{
				cancel(true);
			}
			if (isCancelled()) {

				return 0;
			}
			Log.v("feedback", "3");
			getInput();

			if (isCancelled()) {

				return 0;
			}
			result = feedBackBO.postFeedBack(content, userId);
			return result;
		}

		private void getInput() {
			// TODO Auto-generated method stub
			Log.v("getInput", "start");
			content = feedBackEdit.getText().toString();
			if (feedBackEdit.length() < 1) {
				cancel(true);
			}
			if (isLogin) {
				userId = ding9App.getUserId();
			} else {
				userId = "";// 0表示游客
			}
			Log.v("getInput", "done");
			return;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			Log.v("post", "done");
			pd.dismiss();
			mContext.setProgressBarIndeterminateVisibility(false);
			if (result > 0) {
				Toast.makeText(
						mContext,
						getResources().getString(
								R.string.feed_back_success_tips),
						Toast.LENGTH_SHORT).show();
				finish();
			} else {
				Toast.makeText(
						mContext,
						getResources().getString(
								R.string.server_post_error_tips),
						Toast.LENGTH_SHORT).show();
			}

			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			mContext.setProgressBarIndeterminateVisibility(true);
			pd = new ProgressDialog(mContext);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected void onCancelled() {
			// Toast.makeText(getBaseContext(), "哎呦，没网耶~",
			// Toast.LENGTH_SHORT).show();
			pd.dismiss();
			mContext.setProgressBarIndeterminateVisibility(false);
			if (!isConnect) {
				ToastUtils.show(mContext,
						getResources().getString(R.string.network_error_tips));
			}

			else if (feedBackEdit.length() < 1) {
				ToastUtils.show(mContext,
						getResources().getString(R.string.ask_comment_tips));
				feedBackEdit.startAnimation(shakeAnimation(5));
			}
			super.onCancelled();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed_back, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_send) {
			// 开个asy传数据
			new PostFeedBackTask().execute();
			return true;
		}
		if (id == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 晃动动画
	 * 
	 * @param counts
	 *            半秒钟晃动多少下
	 * @return
	 */
	public static Animation shakeAnimation(int counts) {
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(500);
		return translateAnimation;
	}

}
