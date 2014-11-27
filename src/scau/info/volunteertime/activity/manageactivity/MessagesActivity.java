package scau.info.volunteertime.activity.manageactivity;

import java.util.ArrayList;
import java.util.LinkedList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.MessageBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.trinea.android.common.util.ToastUtils;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MessagesActivity extends ActionBarActivity {

	private SortedLinkList<Message> sortedLinkList;

	private PullToRefreshListView activityListView;

	private MessagesAdapter messagesAdapter;

	private MessageBO messageBO;

	private boolean hasMore;

	private Activity activity;

	private String userId;
	private RelativeLayout actionView;// ActionBar的view
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		initData();
		
 
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		actionView = (RelativeLayout) getLayoutInflater().inflate(
				R.layout.action_bar_title_manager, null);
		getSupportActionBar().setCustomView(
				actionView,
				new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		
		activityListView = (PullToRefreshListView) findViewById(R.id.activity_center_list);

		activityListView.setAdapter(messagesAdapter);

		activityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ResultsExhibition-onCreate-setOnItemClickListener",
						"onItemClick");
				showMessageContent(position);
			}

		});

		activityListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new GetDataTask(userId).execute();
					}
				});

		initListView();

		if (messagesAdapter.getCount() == 0) {
			Log.d("chao-onCreateView", "setState");
			new GetLocalDataTask(userId).execute();
			activityListView.setState(State.REFRESHING, true);
			activityListView.setHeaderScroll(-84);
			new GetDataTask(userId).execute();
		}
	}

	private void initListView() {
		ILoadingLayout startLabels = activityListView.getLoadingLayoutProxy(
				true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新");// 下来达到一定距离时，显示的提示
	}

	private void showMessageContent(int position) {
		Message message = sortedLinkList.get(position - 1);
		Log.d("showActivityDateContent", "activityDate.getId" + message.getId());
		Intent mIntent = new Intent(activity, ShowMessageActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(ShowMessageActivity.SER_KEY, message);
		mIntent.putExtras(mBundle);

		startActivity(mIntent);

		message.setIs_send(2);

		messagesAdapter.notifyDataSetChanged();
	}

	private void initData() {
		hasMore = true;
		activity = this;

		userId = ((Ding9App) getApplicationContext()).getUserId();

		Log.d("ManageActivity-initData", "userId = " + userId);

		messageBO = new MessageBO();
		sortedLinkList = new SortedLinkList<Message>();

		messagesAdapter = new MessagesAdapter(activity,
				sortedLinkList.getSortedLinkList());

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
			View rootView = inflater.inflate(R.layout.fragment_messages,
					container, false);
			return rootView;
		}
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;

		private String userId;

		/**
		 * @param userId
		 */
		public GetDataTask(String userId) {
			this.userId = userId;
		}

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

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
			messagesAdapter.setListData(sortedLinkList.getSortedLinkList());

			messagesAdapter.notifyDataSetChanged();

			activityListView.onRefreshComplete();
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接不正常");
			} else if (!hasMore) {
				ToastUtils.show(activity, "没有更多信息了~");
			}
			activityListView.onRefreshComplete();
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			toGetNewDataFromNet();
		}

		/**
		 * 
		 */
		private void toGetNewDataFromNet() {
			Log.d("doInBackgroundFunction", "getNewData1");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<Message> messages = messageBO.getNewData(userId);
			if (messages != null) {
				sortedLinkList = new SortedLinkList<Message>();
				sortedLinkList.addAll(messages);
				toSaveDataInDatabase(sortedLinkList.getSortedLinkList());
			}
			Log.d("doInBackgroundFunction", "getNewData2");
		}

		private void toSaveDataInDatabase(LinkedList<Message> messages) {

			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			for (Message message : messages) {
				db.execSQL(
						"REPLACE INTO messages(id, receive_user_id , launch_user_id , title , content  , launch_time , is_send) VALUES(?,?,?,?,?,?,?)",
						new Object[] { message.getId(),
								message.getReceive_user_id(),
								message.getLaunch_user_id(),
								message.getTitle(), message.getContent(),
								message.getLaunch_time(), message.getIs_send() });
			}

			db.close();

		}
	}

	private class GetLocalDataTask extends AsyncTask<Void, Void, Void> {

		ArrayList<Message> messages = null;

		private String userId;

		/**
		 * @param userId
		 */
		public GetLocalDataTask(String userId) {
			this.userId = userId;
		}

		@Override
		protected Void doInBackground(Void... params) {
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
			messagesAdapter.setListData(sortedLinkList.getSortedLinkList());

			messagesAdapter.notifyDataSetChanged();
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			toCheckDatabase();
		}

		/**
		 * 
		 */
		private void toCheckDatabase() {
			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			Cursor c = db.rawQuery(
					"SELECT * FROM messages where receive_user_id = '" + userId
							+ "' ORDER BY launch_time DESC", null);
			messages = new ArrayList<Message>();
			c.moveToFirst();
			while (!c.isAfterLast()) {

				Message message = new Message(c.getInt(c.getColumnIndex("id")),
						c.getString(c.getColumnIndex("receive_user_id")),
						c.getString(c.getColumnIndex("launch_user_id")),
						c.getString(c.getColumnIndex("title")), c.getString(c
								.getColumnIndex("content")), c.getLong(c
								.getColumnIndex("launch_time")), c.getInt(c
								.getColumnIndex("is_send")));

				messages.add(message);
				c.moveToNext();
			}
			c.close();
			db.close();

			sortedLinkList.addAll(messages);
		}
	}

	private class UpdateReadNumDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private int id;

		public UpdateReadNumDataTask(int id) {
			this.id = id;
		}

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

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
			messagesAdapter.notifyDataSetChanged();
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
			messageBO.updateReadNum(id);
		}
	}

}
