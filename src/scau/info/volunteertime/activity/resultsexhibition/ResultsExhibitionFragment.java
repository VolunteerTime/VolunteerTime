/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-14
 */
package scau.info.volunteertime.activity.resultsexhibition;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.ResultBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.Result;
import android.app.Activity;
import android.content.ContentValues;
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
import cn.trinea.android.common.util.ToastUtils;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author 蔡超敏
 * 
 */
public class ResultsExhibitionFragment extends Fragment {

	private SortedLinkList<Result> sortedLinkList;
	private SortedLinkList<Result> resultsStorage;// 临时数据的分页类

	private int currentPageSize = 8;
	private int currentPageNumber = 1;

	private PullToRefreshListView resultsListView;

	private ResultsExhibitionListAdapter resultsExhibitionListAdapter;

	private ResultBO resultBO;

	private boolean hasMore;

	private Activity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d("ResultsExhibition", "create");

		hasMore = true;// �и������ʾ��û������ʾ
		activity = getActivity();

		resultBO = new ResultBO();

		resultsStorage = new SortedLinkList<Result>();
		sortedLinkList = new SortedLinkList<Result>();

		resultsExhibitionListAdapter = new ResultsExhibitionListAdapter(
				activity, sortedLinkList.getList());// �ɹ����ݴ���Adapter

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_results_exhibition, null);

		resultsListView = (PullToRefreshListView) view
				.findViewById(R.id.results_exhibition_list);

		resultsListView.setAdapter(resultsExhibitionListAdapter);

		resultsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ResultsExhibition-onCreate-setOnItemClickListener",
						"onItemClick");
				showResultContent(position);
			}

		});

		resultsListView.setMode(Mode.BOTH);
		resultsListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				Log.d("ResultsExhibition-onCreate-setOnRefreshListener",
						"onPullDownToRefresh");
				new GetDataTask(true).execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				Log.d("ResultsExhibition-onCreate-onPullUpToRefresh",
						"onPullUpToRefresh");
				new GetDataTask(false).execute();
			}
		});

		initListView();

		if (resultsExhibitionListAdapter.getCount() == 0) {
			Log.d("chao-onCreateView", "setState");
			new GetLocalDataTask().execute();
			resultsListView.setState(State.REFRESHING, true);
			resultsListView.setHeaderScroll(-84);
			new GetDataTask(true).execute();
		}

		return view;
	}

	private void showResultContent(int position) {
		Result result = sortedLinkList.get(position - 1);
		Log.d("showResultContent", "result.getArticleId" + result.getId());
		Intent mIntent = new Intent(activity, ShowResultActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putSerializable(ShowResultActivity.SER_KEY, result);
		mIntent.putExtras(mBundle);

		startActivity(mIntent);

		new UpdateReadNumDataTask(result.getId()).execute();
		result.setReadNum(result.getReadNum() + 1);
	}

	private void initListView() {
		ILoadingLayout startLabels = resultsListView.getLoadingLayoutProxy(
				true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在载入...");// 刷新时
		startLabels.setReleaseLabel("放开刷新");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = resultsListView.getLoadingLayoutProxy(false,
				true);
		endLabels.setPullLabel("上拉刷新");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在载入...");// 刷新时
		endLabels.setReleaseLabel("放开刷新");// 下来达到一定距离时，显示的提示

	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private boolean isDropDown;

		private String firstTime;
		private String endTime;

		ArrayList<Result> results = null;

		public GetDataTask(boolean isDropDown) {
			Log.d("GetDataTask-GetDataTask", "isDropDown = " + isDropDown);
			this.isDropDown = isDropDown;
		}

		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);

			Log.d("GetDataTask-doInBackground", "isConnect = " + isConnect);
			if (!isConnect) {// ��������޸���������ȡ������
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
			if (isDropDown) {
				resultsExhibitionListAdapter.setListData(sortedLinkList
						.getList());

				resultsExhibitionListAdapter.notifyDataSetChanged();

				resultsListView.onRefreshComplete();
			} else {
				resultsExhibitionListAdapter.setListData(sortedLinkList
						.getList());

				resultsExhibitionListAdapter.notifyDataSetChanged();

				resultsListView.onRefreshComplete();
			}

			new UpdateNowDataTask().execute();
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接不正常");
			} else if (!hasMore) {
				// resultsListView.setFooterNoMoreText("û�и��������Ϣ��Ŷ~");
				ToastUtils.show(activity, "没有更多信息了~");
			}
			if (isDropDown) {
				resultsListView.onRefreshComplete();
			} else {
				resultsListView.onRefreshComplete();
			}
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			if (isDropDown) {
				if (sortedLinkList.isEmpty()) {
					toGetNewDataFromNet();
					toAddDataFromPagination();
				} else {
					toGetUpdateDataFromNet();
				}
			} else {
				if (!hasMore) {
					Log.d("doInBackground", "hasMore not");
					cancel(true);
					return;
				}
				if (sortedLinkList.isEmpty()) {
					toCheckDatabase();
					if (resultsStorage.isEmpty()) {
						toGetNewDataFromNet();
					}
					toAddDataFromPagination();
				} else if (!resultsStorage.isEmpty()) {
					toAddDataFromPagination();
				} else if (resultsStorage.isEmpty()) {
					toGetDataFromNet();
					if (!resultsStorage.isEmpty())
						toAddDataFromPagination();
				}
			}
		}

		/**
		 * 
		 */
		private void toGetUpdateDataFromNet() {
			Log.d("doInBackgroundFunction", "toGetUpdateDataFromNet1");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			firstTime = sortedLinkList.get(0).getPublishTime() + "";
			Log.d("doInBackgroundFunction-toGetUpdateDataFromNet",
					"firstTime =" + firstTime);
			results = resultBO.getDropDownData(firstTime, currentPageSize);
			if (results != null) {
				sortedLinkList.addAll(results);
				toSaveDataInDatabase();
			}
			Log.d("doInBackgroundFunction", "toGetUpdateDataFromNet2");
		}

		/**
		 * 
		 */
		private void toGetDataFromNet() {
			Log.d("doInBackgroundFunction", "toGetDataFromNet");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			results = new ArrayList<Result>();
			endTime = sortedLinkList.get(sortedLinkList.size() - 1).getPublishTime() + "";
			Log.d("doInBackgroundFunction-toGetDataFromNet", "endTime ="
					+ endTime);
			results = (ArrayList<Result>) resultBO.getDownData(endTime,
					currentPageSize);
			if (results != null && results.size() != 0) {
				resultsStorage.addAll(results);
				toSaveDataInDatabase();
			} else {
				Log.d("Cancel", "no more data");
				hasMore = false;
				cancel(true);
			}
		}

		/**
		 * 
		 */
		private void toAddDataFromPagination() {
			if (!resultsStorage.isEmpty()) {
				sortedLinkList
						.addAll(resultsStorage.getsubList(currentPageSize));
			}
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

			results = new ArrayList<Result>();
			results = (ArrayList<Result>) resultBO.getNewData(currentPageSize);
			if (results != null) {
				resultsStorage.addAll(results);
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
			for (Result result : results) {
				db.execSQL(
						"REPLACE INTO results(id ,title ,content ,image  ,editor ,publishTime, readNum) VALUES(?,?,?,?,?,?,?)",
						new Object[] { result.getId(), result.getTitle(),
								result.getContent(), result.getImage(),
								result.getEditor(), result.getPublishTime(),
								result.getReadNum() });
			}
			db.close();
		}

		/**
		 * 
		 */
		private void toCheckDatabase() {
			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			Cursor c = db.rawQuery(
					"SELECT * FROM results ORDER BY publishTime DESC", null);
			results = new ArrayList<Result>();
			if (c.moveToFirst())
				firstTime = c.getString(c.getColumnIndex("publishTime"));
			while (!c.isAfterLast()) {
				Result result = new Result(c.getInt(c.getColumnIndex("id")),
						c.getString(c.getColumnIndex("title")), c.getString(c
								.getColumnIndex("content")), c.getString(c
								.getColumnIndex("image")), c.getString(c
								.getColumnIndex("editor")), c.getLong(c
								.getColumnIndex("publishTime")), c.getInt(c
								.getColumnIndex("readNum")));
				results.add(result);
				c.moveToNext();
			}
			if (c.moveToLast())
				endTime = c.getString(c.getColumnIndex("publishTime"));
			Log.d("ResultsExhibitionFragment-doInBackgroundFunction",
					"firstTime = " + firstTime + "; endTime = " + endTime);
			c.close();
			db.close();
			resultsStorage.addAll(results);
		}
	}

	private class GetLocalDataTask extends AsyncTask<Void, Void, Void> {

		ArrayList<Result> results = null;

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
			resultsExhibitionListAdapter.setListData(sortedLinkList.getList());

			resultsExhibitionListAdapter.notifyDataSetChanged();
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
			Log.d("doInBackgroundFunction", "size = " + resultsStorage.size());
			toCheckDatabase();
			toAddDataFromPagination();
		}

		/**
		 * 
		 */
		private void toAddDataFromPagination() {
			Log.d("doInBackgroundFunction-toAddDataFromPagination", "size = "
					+ resultsStorage.size());
			if (!resultsStorage.isEmpty()) {
				sortedLinkList.addAll(resultsStorage
						.getsubList(currentPageSize));
			}
		}

		/**
		 * 
		 */
		private void toCheckDatabase() {
			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			Cursor c = db.rawQuery(
					"SELECT * FROM results ORDER BY publishTime DESC", null);
			results = new ArrayList<Result>();
			c.moveToFirst();
			while (!c.isAfterLast()) {
				Result result = new Result(c.getInt(c.getColumnIndex("id")),
						c.getString(c.getColumnIndex("title")), c.getString(c
								.getColumnIndex("content")), c.getString(c
								.getColumnIndex("image")), c.getString(c
								.getColumnIndex("editor")), c.getLong(c
								.getColumnIndex("publishTime")), c.getInt(c
								.getColumnIndex("readNum")));
				results.add(result);
				c.moveToNext();
			}
			c.close();
			db.close();
			resultsStorage.addAll(results);
		}
	}

	private class UpdateNowDataTask extends AsyncTask<Void, Void, Void> {

		ArrayList<Result> results = null;
		private boolean isConnect;

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
			resultsExhibitionListAdapter.setListData(sortedLinkList.getList());

			resultsExhibitionListAdapter.notifyDataSetChanged();
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
			JSONArray array = new JSONArray();
			for (Result result : sortedLinkList.getSortedLinkList()) {
				JSONObject jsonObj = new JSONObject();
				try {
					jsonObj.put("id", result.getId());
				} catch (JSONException e) {
					Log.d("UpdateNowDataTask",
							"result.getId() = " + result.getId());
					e.printStackTrace();
				}
				array.put(jsonObj);
			}

			JSONObject json = new JSONObject();
			try {
				json.put("array", array);
			} catch (JSONException e) {
				Log.d("UpdateNowDataTask", "array");
				e.printStackTrace();
			}

			String jsonResults = resultBO.getUpdateDate(json.toString());
			SQLiteDatabase db = activity.openOrCreateDatabase(
					"volunteertimedatabase.db", Context.MODE_PRIVATE, null);
			try {

				JSONObject jsonObject = new JSONObject(jsonResults);
				JSONArray jsonArray = jsonObject.getJSONArray("array");

				JSONObject jsonResult;
				int count = 0;

				for (int i = 0; i < jsonArray.length(); i++) {
					jsonResult = jsonArray.getJSONObject(i);
					int id = jsonResult.getInt("id");
					int readNum = jsonResult.getInt("read_num");
					Log.d("UpdateNowDataTask-doInBackgroundFunction", "i = "
							+ i + " id = " + id + " readNum = " + readNum);

					if (readNum < 0) {
						sortedLinkList.getSortedLinkList().remove(i + count);

						count--;
						db.delete("results", "id = ?",
								new String[] { String.valueOf(id) });
					} else {
						sortedLinkList.get(i + count).setReadNum(readNum);

						ContentValues values = new ContentValues();
						values.put("readNum", readNum);

						db.update("results", values, "id = ?",
								new String[] { String.valueOf(id) });
					}

				}

			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}

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
			resultsExhibitionListAdapter.setListData(sortedLinkList.getList());

			resultsExhibitionListAdapter.notifyDataSetChanged();
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
			resultBO.updateReadNum(id);
		}
	}

}
