package scau.info.volunteertime.activity.resultsexhibition;

import java.util.ArrayList;

import cn.trinea.android.common.util.ToastUtils;
import scau.info.volunteertime.R;
import scau.info.volunteertime.util.AgoTimeUtil;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.vo.Result;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShowResultActivity extends ActionBarActivity {

	public static final String SER_KEY = "RESULT";

	private Result result;

	private WebView webViewShowContent;

	private String htmlStr;
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		result = (Result) getIntent().getSerializableExtra(SER_KEY);

		this.setTitle("成果展示");

		setContentView(R.layout.activity_show_result);
		webViewShowContent = (WebView) findViewById(R.id.show_content);

		mContext=this;
		result.setReadNum(result.getReadNum());
		showContent();
		

	}

	/**
	 * 使用WebView显示html内容
	 */
	private void showContent() {
		String author = result.getEditor();

		htmlStr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>"
				+ "</title></head><body><h4 align=\"center\">"
				+ result.getTitle()
				+ "</h4><p align=\"center\"><font size=\"2\" color=\"#808080\">"
				+ (author.equals("null") ? "" : author)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ AgoTimeUtil.getTimeAgoFromCurren(result.getDate())
				+ "</font></p> <p align=\"center\"><img  src=\""
				+ result.getImage()
				+ "\" /></p>&nbsp;&nbsp;&nbsp;&nbsp;"
				+ result.getContent()
				+ "</p><p align=\"left\"><font size=\"2\" color=\"#808080\">阅读 "
				+ result.getReadNum() + "</font></p></body></html>";

		webViewShowContent.loadDataWithBaseURL("about:blank", htmlStr,
				"text/html", "utf-8", null);
		// .loadData(fmtString(htmlStr), "text/html", "utf-8");

		webViewShowContent.setWebViewClient(new WebViewClientEmb());

		WebSettings settings = webViewShowContent.getSettings();
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_result, menu);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_show_result,
					container, false);
			return rootView;
		}
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
	
	private class SaveDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(mContext);

			Log.d("GetDataTask-doInBackground", "isConnect = " + isConnect);
			if (!isConnect) {
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}

			Log.d("GetDataTask-doInBackground", "in");
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
			Log.d("SaveDataTask-postFunction", "success!");
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(mContext, "网络连接不正常");
			}
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			
		}

	}

}
