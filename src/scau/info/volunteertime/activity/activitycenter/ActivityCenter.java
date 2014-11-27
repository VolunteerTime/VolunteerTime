/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.activitycenter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.activitycenter.ActivityAdapter.OnParticipateButtonListener;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.ActivityCenterBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.ActivityDate;
import scau.info.volunteertime.vo.ActivityGroup;
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
import android.widget.Button;
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
					public void onParticipate(int activityId, int position,
							View v) {
						Log.d("ActivityCenter-onCreate-setParticipateButtonListener",
								"onParticipate position = " + position);
						participateActivity(activityId, position, v);
					}

					@Override
					public void onQuit(int activityId, int position, View v) {
						Log.d("ActivityCenter-onCreate-setParticipateButtonListener",
								"onQuit position = " + position);
						quitParticipateActivity(activityId, position, v);
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
		Intent mIntent = new Intent(activity, ShowActivityActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(ShowActivityActivity.SER_KEY, activityDate);
		mIntent.putExtras(mBundle);

		startActivity(mIntent);

		new UpdateReadNumDataTask(activityDate.getId()).execute();
		activityDate.setReadNum(activityDate.getReadNum() + 1);
	}

	private void participateActivity(int activityId, int position, View v) {
		String userId = geiUserId();
		new ParticipateAndCheckTask(userId, activityId, position, v).execute();
	}

	private void quitParticipateActivity(int activityId, int position, View v) {
		String userId = geiUserId();
		new QuitParticipateAndCheckTask(userId, activityId, position, v)
				.execute();
	}

	/**
	 * @return String
	 */
	private String geiUserId() {
		return ((Ding9App) activity.getApplicationContext()).getUserId();
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
				toSaveDataInDatabase(activityDates);
				sortedLinkList = new SortedLinkList<ActivityDate>();
				sortedLinkList.addAll(activityDates);
			}
			Log.d("doInBackgroundFunction", "getNewData2");
		}

		/**
		 * @param activityDates
		 * @param results
		 */
		private void toSaveDataInDatabase(ArrayList<ActivityDate> activityDates) {

			ArrayList<Integer> integers = new ArrayList<Integer>();

			for (ActivityDate activityDate : sortedLinkList.getSortedLinkList()) {
				integers.add(activityDate.getId());
			}

			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			for (ActivityDate activityDate : activityDates) {
				db.execSQL(
						"REPLACE INTO activities(id , title , content ,  image , editor , publishTime , endTime , limitNum , readNum ,  groupId , participatorsNum ) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { activityDate.getId(),
								activityDate.getTitle(),
								activityDate.getContent(),
								activityDate.getImage(),
								activityDate.getEditor(),
								activityDate.getPublishTime(),
								activityDate.getEndTime(),
								activityDate.getLimitNum(),
								activityDate.getReadNum(),
								activityDate.getGroupId(),
								activityDate.getParticipatorsNum() });
				if (activityDate.getGroupId() != 0) {
					db.execSQL(
							"REPLACE INTO activity_group(id , principal_id , participators) VALUES(?,?,?)",
							new Object[] {
									activityDate.getGroupId(),
									activityDate.getActivityGroup()
											.getPrincipalId(),
									activityDate.getActivityGroup()
											.getParticipators() });
				}
				if (integers.contains(activityDate.getId())) {
					integers.remove(Integer.valueOf(activityDate.getId()));
				}
			}

			for (Integer id : integers) {
				db.delete("activities", "id = ?",
						new String[] { String.valueOf(id) });
				Log.d("toSaveDataInDatabase", "id = " + id);
			}

			db.close();
			/*
			 * 
			 * db.delete("results", "id = ?", new String[] { String.valueOf(id)
			 * });
			 */
		}

	}

	private class ParticipateAndCheckTask extends AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String userId;

		private int activityId;

		private int position;

		private View button;

		public ParticipateAndCheckTask(String userId, int activityId,
				int position, View v) {
			this.userId = userId;
			this.activityId = activityId;
			this.position = position;
			this.button = v;
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
				try {
					JSONObject json = new JSONObject();
					if (sortedLinkList.get(position).getActivityGroup() != null)
						json = new JSONObject(sortedLinkList.get(position)
								.getActivityGroup().getParticipators());
					JSONArray array = json.getJSONArray("userIds");

					JSONObject jsonObj = new JSONObject();
					jsonObj.put("userId", userId);
					array.put(jsonObj);
					json.put("userIds", array);
					sortedLinkList.get(position).getActivityGroup()
							.setParticipators(json.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				ToastUtils.show(activity, "报名成功");
				sortedLinkList.get(position).setParticipatorsNum(
						sortedLinkList.get(position).getParticipatorsNum() + 1);

				if (button instanceof Button) {
					((Button) button).setText("取消报名");
					((Button) button).setBackgroundResource(R.drawable.unbaoming);
				}
				activityListAdapter.notifyDataSetChanged();

			} else if (result.trim().equals("failure")) {
				ToastUtils.show(activity, "报名失败");
			} else {
				ToastUtils.show(activity, "报名失败");
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

	private class QuitParticipateAndCheckTask extends
			AsyncTask<Void, Void, String> {

		private boolean isConnect;

		private String userId;

		private int activityId;

		private int position;

		private View button;

		public QuitParticipateAndCheckTask(String userId, int activityId,
				int position, View v) {
			this.userId = userId;
			this.activityId = activityId;
			this.position = position;
			this.button = v;
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
		 * "success";
		 * 
		 * "noIdInActivityGroup";
		 * 
		 * "hasReadyQuit";
		 * 
		 * "failure";
		 * 
		 * @param result
		 */
		private void postFunction(String result) {
			Log.d("postFunction", "result = " + result);
			if (result.trim().equals("success")) {

				String json = sortedLinkList.get(position).getActivityGroup()
						.getParticipators();
				json = json.replace(",{\"userId\":\"chao2\"},", "");
				json = json.replace("{\"userId\":\"chao2\"},", "");
				json = json.replace(",{\"userId\":\"chao2\"}", "");
				json = json.replace("{\"userId\":\"chao2\"}", "");
				Log.d("postFunction", "json 1 " + json);

				sortedLinkList.get(position).getActivityGroup()
						.setParticipators(json);

				ToastUtils.show(activity, "取消报名成功");
				Log.d("postFunction", "success 1");
				if (button instanceof Button) {
					Log.d("postFunction", "success 2");
					((Button) button).setText("报名");
					((Button) button).setBackgroundResource(R.drawable.baoming);
				}
				sortedLinkList.get(position).setParticipatorsNum(
						sortedLinkList.get(position).getParticipatorsNum() - 1);

				activityListAdapter.notifyDataSetChanged();

			} else if (result.trim().equals("failure")) {
				if (button instanceof Button) {
					((Button) button).setText("报名");
					 
					((Button) button).setBackgroundResource(R.drawable.baoming);
					System.out.println("baoaaoaoaoaooaoaoaooaoaoaoa");
				 
				}
				ToastUtils.show(activity, "该活动已注销");
			} else if (result.trim().equals("noIdInActivityGroup")) {
				if (button instanceof Button) {
					((Button) button).setText("报名");
					((Button) button).setBackgroundResource(R.drawable.baoming);
				}
				ToastUtils.show(activity, "早已取消报名了");
			} else if (result.trim().equals("hasReadyQuit")) {
				if (button instanceof Button) {
					((Button) button).setText("报名");
					((Button) button).setBackgroundResource(R.drawable.baoming);
				}
				ToastUtils.show(activity, "早已取消报名了");
			} else {
				if (button instanceof Button) {
					((Button) button).setText("报名");
					((Button) button).setBackgroundResource(R.drawable.baoming);
				}
				ToastUtils.show(activity, "取消报名");
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
			return activityCenterBO.quitParticipateActivity(userId, activityId);
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
				int groupId = c.getInt(c.getColumnIndex("groupId"));
				ActivityDate activityDate = new ActivityDate(c.getInt(c
						.getColumnIndex("id")), c.getString(c
						.getColumnIndex("title")), c.getString(c
						.getColumnIndex("content")), c.getString(c
						.getColumnIndex("image")), c.getString(c
						.getColumnIndex("editor")), c.getLong(c
						.getColumnIndex("publishTime")), c.getLong(c
						.getColumnIndex("endTime")), c.getInt(c
						.getColumnIndex("limitNum")), c.getInt(c
						.getColumnIndex("readNum")), groupId, c.getInt(c
						.getColumnIndex("participatorsNum")));
				Log.d("toCheckDatabase", "groupId = " + groupId);
				if (groupId != 0) {
					Cursor c2 = db.rawQuery(
							"SELECT * FROM activity_group where id = "
									+ groupId, null);
					c2.moveToFirst();
					if (!c2.isAfterLast()) {
						ActivityGroup activityGroup = new ActivityGroup(
								groupId,
								c2.getString(c2.getColumnIndex("principal_id")),
								c2.getString(c2.getColumnIndex("participators")));
						activityDate.setActivityGroup(activityGroup);
					}
				}
				activityDates.add(activityDate);
				c.moveToNext();
			}
			c.close();
			db.close();
			sortedLinkList.addAll(activityDates);
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
			activityListAdapter.notifyDataSetChanged();
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
			activityCenterBO.updateReadNum(id);
		}
	}
}
