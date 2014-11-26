package scau.info.volunteertime.activity.manageactivity;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.MessageBO;
import scau.info.volunteertime.util.AgoTimeUtil;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.Message;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowMessageActivity extends ActionBarActivity {

	public static final String SER_KEY = "MESSAGE";

	private Message message;

	private WebView webViewShowContent;

	private String htmlStr;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		message = (Message) getIntent().getSerializableExtra(SER_KEY);

		this.setTitle("活动中心");

		setContentView(R.layout.activity_show_result);
		webViewShowContent = (WebView) findViewById(R.id.show_content);

		mContext = this;
		showContent();
		Log.d("ShowMessageActivity", "message.getId() = " + message.getId());
		new UpdateReadNumDataTask(message.getId()).execute();

	}

	/**
	 * 使用WebView显示html内容
	 */
	private void showContent() {
		String author = message.getLaunch_user_id();

		htmlStr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>"
				+ "</title></head><body><h4 align=\"center\">"
				+ message.getTitle()
				+ "</h4><p align=\"center\"><font size=\"2\" color=\"#808080\">"
				+ (author.equals("null") ? "" : author)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ AgoTimeUtil.getTimeAgoFromCurren(message.getDate())
				+ "</font></p> &nbsp;&nbsp;&nbsp;&nbsp;"
				+ message.getContent()
				+ "</p></body></html>";

		webViewShowContent.loadDataWithBaseURL("about:blank", htmlStr,
				"text/html", "utf-8", null);
		// .loadData(fmtString(htmlStr), "text/html", "utf-8");

		webViewShowContent.setWebViewClient(new WebViewClientEmb());

		WebSettings settings = webViewShowContent.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);

	}

	class WebViewClientEmb extends WebViewClient {
		// 在WebView中而不是系统默认浏览器中显示页面
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return true;
		}

		// 页面载入前调用
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		// 页面载入完成后调用
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_message, menu);
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

	private class UpdateReadNumDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private int id;

		public UpdateReadNumDataTask(int id) {
			this.id = id;
		}

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mContext);

			Log.d("UpdateNowDataTask-doInBackground", "isConnect = "
					+ isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("UpdateNowDataTask-doInBackground", "in");
			doInBackgroundFunction();
			return null;
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
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
		private void postFunction(Void result) {
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
		private void doInBackgroundFunction() {
			new MessageBO().updateReadNum(id);
		}
	}

}
