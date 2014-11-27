package scau.info.volunteertime.activity.manageactivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.ActivityCenterBO;
import scau.info.volunteertime.business.MessageBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import cn.trinea.android.common.util.ToastUtils;

public class SendMessageActivity extends ActionBarActivity {

	private Activity activity;
	public MessageBO messageBO;

	private EditText etReceiver, etContent, etTitle;
	private Button btSend;

	private String principalId, participators, title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);
		
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		RelativeLayout actionView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.action_bar_title_sendmessage, null);
		
		getSupportActionBar().setCustomView(
				actionView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		principalId = bundle.getString("principalId");
		participators = bundle.getString("participators");
		title = bundle.getString("title");
		Log.d("SendMessageActivity", "principalId = " + principalId
				+ " participators = " + participators);

		etReceiver = (EditText) findViewById(R.id.receiver);
		etContent = (EditText) findViewById(R.id.send_message);
		etTitle = (EditText) findViewById(R.id.message_title);

		btSend = (Button) findViewById(R.id.send_message_button);

		String receiver = "";
		activity = this;
		messageBO = new MessageBO();

		JSONArray array;
		try {
			JSONObject json = new JSONObject(participators);
			array = json.getJSONArray("userIds");
			int len = array.length();
			for (int i = 0; i < len; i++) {
				JSONObject jsonObj = array.getJSONObject(i);
				String userIdByJson = jsonObj.getString("userId");
				receiver += userIdByJson + ";";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		etReceiver.setText(" 收信人:" + receiver);
		etReceiver.setEnabled(false);

		etTitle.setText(title);

		Log.d("SendMessageActivity", "receiver = " + receiver);

		btSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				sendMessageBySender();
			}

		});

	}

	private void sendMessageBySender() {
		String message = etContent.getText().toString();
		if (message == null || message.equals("")) {
			ToastUtils.show(activity, "发送内容不能为空");
		} else if (title == null || title.equals("")) {
			ToastUtils.show(activity, "标题不能为空");
		} else {
			new SendMessageTask(principalId, participators, message, title)
					.execute();
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

	private class SendMessageTask extends AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String principalId, participators, message, title;
		private ProgressDialog mDialog;

		/**
		 * @param principalId
		 * @param participators
		 * @param message
		 */
		public SendMessageTask(String principalId, String participators,
				String message, String title) {
			this.principalId = principalId;
			this.participators = participators;
			this.message = message;
			this.title = title;
			mDialog = new ProgressDialog(activity);

			mDialog.setTitle("提示");
			mDialog.setMessage("发送信息中...");
			mDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

			Log.d("GetDataTask-doInBackground", "isConnect = " + isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return "failure";
			}

			Log.d("GetDataTask-doInBackground", "in");

			return doInBackgroundFunction();
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String result) {
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
		private void postFunction(String result) {
			mDialog.dismiss();
			Log.d("postFunction", "result = " + result);
			if (result.trim().equals("success")) {
				ToastUtils.show(activity, "发送成功");
			} else if (result.trim().equals("failure")) {
				ToastUtils.show(activity, "发送失败");
			} else {
				ToastUtils.show(activity, "报名失败");
			}
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			mDialog.dismiss();
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接不正常");
			}
		}

		/**
		 * 
		 */
		private String doInBackgroundFunction() {
			return messageBO.sendMessages(principalId, participators, message,
					title);
		}

	}

}
