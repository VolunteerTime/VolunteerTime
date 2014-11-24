/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityAdapter.OnParticipateButtonListener;
import scau.info.volunteertime.activity.resultsexhibition.ShowResultActivity;
import scau.info.volunteertime.business.ActivityCenterBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.ActivityDate;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.trinea.android.common.util.ToastUtils;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author 蔡超敏
 * 
 */
public class ActivityCenter extends Fragment {
	private SortedLinkList<ActivityDate> sortedLinkList;

	private PullToRefreshListView activityListView;

	private ActivityAdapter activityListAdapter;

	private ActivityCenterBO activityCenterBO;

	private boolean hasMore;

	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d("ResultsExhibition", "create");

		hasMore = true;
		activity = getActivity();

		activityCenterBO = new ActivityCenterBO();
		sortedLinkList = new SortedLinkList<ActivityDate>();

		activityListAdapter = new ActivityAdapter(activity,
				sortedLinkList.getSortedLinkList());
		activityListAdapter
				.setParticipateButtonListener(new OnParticipateButtonListener() {

					@Override
					public void onParticipate(int activityId, int position) {
						Log.d("ActivityCenter-onCreate-setParticipateButtonListener",
								"onClick position = " + position);
						participateActivity(activityId, position);
					}
				});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_activity_center, null);

		activityListView = (PullToRefreshListView) view
				.findViewById(R.id.activity_center_list);

		activityListView.setAdapter(activityListAdapter);

		activityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ResultsExhibition-onCreate-setOnItemClickListener",
						"onItemClick");
				showActivityDateContent(position);
			}

		});

		activityListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new GetDataTask().execute();
					}
				});

		initListView();

		if (activityListAdapter.getCount() == 0) {
			Log.d("chao-onCreateView", "setState");
			new GetLocalDataTask().execute();
			activityListView.setState(State.REFRESHING, true);
			activityListView.setHeaderScroll(-84);
			new GetDataTask().execute();
		}

		return view;
	}

	private void showActivityDateContent(int position) {
		ActivityDate activityDate = sortedLinkList.get(position - 1);
		Log.d("showActivityDateContent",
				"activityDate.getId" + activityDate.getId());
		Intent mIntent = new Intent(activity, ShowResultActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(ShowResultActivity.SER_KEY, activityDate);
		mIntent.putExtras(mBundle);

		startActivity(mIntent);
	}

	private void participateActivity(int activityId, int position) {
		String userId = geiUserId();
		new ParticipateAndCheckTask(userId, activityId, position).execute();
	}

	/**
	 * @return String
	 */
	private String geiUserId() {
		return "201230560202";
	}

	private void initListView() {
		ILoadingLayout startLabels = activityListView.getLoadingLayoutProxy(
				true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新");// 下来达到一定距离时，显示的提示

	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;

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
			activityListAdapter.setListData(sortedLinkList.getSortedLinkList());

			activityListAdapter.notifyDataSetChanged();

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
			ArrayList<ActivityDate> activityDates = activityCenterBO
					.getNewData();
			if (activityDates != null) {
				sortedLinkList = new SortedLinkList<ActivityDate>();
				sortedLinkList.addAll(activityDates);
				toSaveDataInDatabase();
			}
			Log.d("doInBackgroundFunction", "getNewData2");
		}

		/**
		 * @param results
		 */
		private void toSaveDataInDatabase() {
			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			for (ActivityDate activityDate : sortedLinkList.getList()) {
				db.execSQL(
						"REPLACE INTO activities(id , title , content ,  image , editor , publishTime , endTime , limitNum , readNum ,  groupId , participatorsNum ) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { activityDate.getId(),
								activityDate.getTitle(),
								activityDate.getContent(),
								activityDate.getImage(),
								activityDate.getEditor(),
								activityDate.getPublishTime(),
								activityDate.getEndTime(),
								activityDate.getReadNum(),
								activityDate.getLimitNum(),
								activityDate.getGroupId(),
								activityDate.getParticipatorsNum() });
			}
			db.close();
		}

	}

	private class ParticipateAndCheckTask extends AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String userId;

		private int activityId;

		private int position;

		public ParticipateAndCheckTask(String userId, int activityId,
				int position) {
			this.userId = userId;
			this.activityId = activityId;
			this.position = position;
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
			Log.d("postFunction", "result = " + result);
			if (result.trim().equals("success")) {
				ToastUtils.show(activity, "参加成功");
				sortedLinkList.get(position).setParticipatorsNum(
						sortedLinkList.get(position).getParticipatorsNum() + 1);

				activityListAdapter.notifyDataSetChanged();

				activityListView.onRefreshComplete();
			} else if (result.trim().equals("failure")) {
				ToastUtils.show(activity, "参加失败");
			} else {
				ToastUtils.show(activity, "参加失败");
			}
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
		private String doInBackgroundFunction() {
			return activityCenterBO.participateActivity(userId, activityId);
		}

	}

	private class GetLocalDataTask extends AsyncTask<Void, Void, Void> {

		ArrayList<ActivityDate> activityDates = null;

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
			activityListAdapter.setListData(sortedLinkList.getSortedLinkList());

			activityListAdapter.notifyDataSetChanged();
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
					"SELECT * FROM activities ORDER BY publishTime DESC", null);
			activityDates = new ArrayList<ActivityDate>();
			c.moveToFirst();
			while (!c.isAfterLast()) {
				ActivityDate activityDate = new ActivityDate(c.getInt(c
						.getColumnIndex("id")), c.getString(c
						.getColumnIndex("title")), c.getString(c
						.getColumnIndex("content")), c.getString(c
						.getColumnIndex("image")), c.getString(c
						.getColumnIndex("editor")), c.getLong(c
						.getColumnIndex("publishTime")), c.getLong(c
						.getColumnIndex("endTime")), c.getInt(c
						.getColumnIndex("limitNum")), c.getInt(c
						.getColumnIndex("readNum")), c.getInt(c
						.getColumnIndex("groupId")), c.getInt(c
						.getColumnIndex("participatorsNum")));
				activityDates.add(activityDate);
				c.moveToNext();
			}
			c.close();
			db.close();
			sortedLinkList.addAll(activityDates);
		}
	}
}
