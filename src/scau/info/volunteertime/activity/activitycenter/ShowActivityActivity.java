package scau.info.volunteertime.activity.activitycenter;

import scau.info.volunteertime.R;
import scau.info.volunteertime.util.AgoTimeUtil;
import scau.info.volunteertime.vo.ActivityDate;
import scau.info.volunteertime.vo.Result;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class ShowActivityActivity extends ActionBarActivity {

	public static final String SER_KEY = "ACTIVITY";

	private ActivityDate activityDate;

	private WebView webViewShowContent;

	private String htmlStr;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		activityDate = (ActivityDate) getIntent().getSerializableExtra(SER_KEY);

		this.setTitle("活动中心");

		setContentView(R.layout.activity_show_result);
		webViewShowContent = (WebView) findViewById(R.id.show_content);

		mContext = this;
		activityDate.setReadNum(activityDate.getReadNum());
		showContent();

	}

	/**
	 * 使用WebView显示html内容
	 */
	private void showContent() {
		String author = activityDate.getEditor();

		htmlStr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /><title>"
				+ "</title></head><body><h4 align=\"center\">"
				+ activityDate.getTitle()
				+ "</h4><p align=\"center\"><font size=\"2\" color=\"#808080\">"
				+ (author.equals("null") ? "" : author)
				+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ AgoTimeUtil.getTimeAgoFromCurren(activityDate.getDate())
				+ "</font></p> <p align=\"center\"><img  src=\""
				+ activityDate.getImage()
				+ "\" /></p>&nbsp;&nbsp;&nbsp;&nbsp;"
				+ activityDate.getContent()
				+ "</p><p align=\"left\"><font size=\"2\" color=\"#808080\">阅读 "
				+ activityDate.getReadNum() + "</font></p></body></html>";

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
		getMenuInflater().inflate(R.menu.show_activity, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_show_activity,
					container, false);
			return rootView;
		}
	}

}
