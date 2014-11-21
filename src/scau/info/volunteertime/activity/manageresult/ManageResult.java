/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-15
 */
package scau.info.volunteertime.activity.manageresult;

import java.util.ArrayList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.activity.manageresult.ManageResultsExhibitionListAdapter.OnSettingsOnClickListener;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.business.ResultBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.util.SortedLinkList;
import scau.info.volunteertime.vo.Result;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import cn.trinea.android.common.util.ToastUtils;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

/**
 * @author 蔡超敏
 * 
 */
public class ManageResult extends ActionBarActivity {

	private SortedLinkList<Result> sortedLinkList;
	private Pagination<Result> resultsPagination;// 临时数据的分页类

	private int currentPageSize = 8;
	private int currentPageNumber = 1;

	private DropDownListView resultsListView;

	private ManageResultsExhibitionListAdapter resultsExhibitionListAdapter;

	private ResultBO resultBO;

	private boolean hasMore;

	private Activity activity;

	private boolean isAddState = true;
	private MenuItem item;

	private AddResultFragment addResultFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		actionBarInit();

		setContentView(R.layout.activity_manage_result);

		Log.d("ResultsExhibition", "create");

		hasMore = true;// no data to set
		activity = this;

		resultBO = new ResultBO();// resultBO
		resultsPagination = new Pagination<Result>();
		resultsPagination.setCurrentPageNumber(currentPageNumber);
		resultsPagination.setPageSize(currentPageSize);
		sortedLinkList = new SortedLinkList<Result>();

		resultsExhibitionListAdapter = new ManageResultsExhibitionListAdapter(
				activity, sortedLinkList.getList());

		resultsExhibitionListAdapter
				.setOnSettingsClickListener(new OnSettingsOnClickListener() {

					@Override
					public void onClick(int position) {
						Log.d("ManageResultsExhibitionFragment-setOnSettingsClickListener",
								"position = " + position);
					}
				});

		ListViewInit();

	}

	/**
	 * 
	 */
	private void actionBarInit() {
		ActionBar bar = this.getSupportActionBar();
		this.setTitle("管理成果");
		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_CUSTOM);
	}

	/**
	 * ListView初始化
	 */
	private void ListViewInit() {
		resultsListView = (DropDownListView) findViewById(R.id.results_exhibition_list);

		resultsListView.setAdapter(resultsExhibitionListAdapter);

		resultsListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d("ResultsExhibition-onCreate-setOnItemLongClickListener-onItemLongClick",
								"position = " + position);
						return false;
					}

				});
		resultsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ResultsExhibition-onCreate-setOnItemClickListener-onItemClick",
						"position = " + position);
			}
		});

		resultsListView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("ResultsExhibition-onCreate-setOnBottomListener-onClick",
						"上拉");
				new GetDataTask(false).execute();
			}
		});

		resultsListView.onBottom();

		resultsListView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				Log.d("ResultsExhibition-onCreate-setOnDropDownListener-onClick",
						"下拉");
				new GetDataTask(true).execute();
			}
		});
		resultsListView.onDropDownComplete();
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private boolean isDropDown;

		private String firstTime;
		private String endTime;

		ArrayList<Result> results = null;

		public GetDataTask(boolean isDropDown) {
			this.isDropDown = isDropDown;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);// 检查网络
			if (!isConnect) {// 网络不通则跳出
				Log.d("doInBackground", "isConnect not");
				cancel(true);
				return null;
			}
			Log.d("GetDataTask-doInBackground", "in");
			doInBackgroundFunction();
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onCancelled()
		 */
		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
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

				resultsListView.onDropDownComplete();
			} else {
				resultsExhibitionListAdapter.setListData(sortedLinkList
						.getList());

				resultsExhibitionListAdapter.notifyDataSetChanged();

				resultsListView.onBottomComplete();
			}
		}

		/**
		 * 
		 */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络无连接");
			} else if (!hasMore) {
				resultsListView.setFooterNoMoreText("没有更多数据了~");
				ToastUtils.show(activity, "没有更多数据了~");
			}
			if (isDropDown) {
				resultsListView.onDropDownComplete();
			} else {
				resultsListView.onBottomComplete();
			}
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			Log.d("doInBackgroundFunction",
					"getSumPage = " + resultsPagination.getSumPage());
			if (isDropDown) {
				if (sortedLinkList.isEmpty()) {
					toGetNewDataFromNet();
					toAddDataFromPagination();
				} else {
					toGetUpdateDataFromNet();
				}
			} else {
				if (!hasMore) {// 没有更多数据则不通过
					Log.d("doInBackground", "hasMore not");
					cancel(true);
					return;
				}
				if (sortedLinkList.isEmpty()) {
					toCheckDatabase();
					if (resultsPagination.getAmountOfRecorders() == 0) {
						toGetNewDataFromNet();
					}
					toAddDataFromPagination();
				} else if (resultsPagination.getCurrentPageNumber() <= resultsPagination
						.getSumPage()) {
					toAddDataFromPagination();
				} else if (resultsPagination.getCurrentPageNumber() > resultsPagination
						.getSumPage()) {
					toGetDataFromNet();
				}
			}
		}

		/**
		 * 
		 */
		private void toGetUpdateDataFromNet() {
			Log.d("doInBackgroundFunction", "toGetUpdateDataFromNet1");

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
			results = new ArrayList<Result>();
			endTime = resultsPagination.getLastData().getPublishTime() + "";
			Log.d("doInBackgroundFunction-toGetDataFromNet", "endTime ="
					+ endTime);
			results = (ArrayList<Result>) resultBO.getDownData(endTime,
					currentPageSize);
			if (results != null && results.size() != 0) {
				resultsPagination.setRecords(results);
				resultsPagination.setCurrentPageNumber(currentPageNumber);
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
			Log.d("doInBackgroundFunction",
					"toAddDataFromPagination PageNumber="
							+ resultsPagination.getCurrentPageNumber());
			if (resultsPagination.getAmountOfRecorders() != 0) {
				sortedLinkList
						.addAll(resultsPagination.getcurrentPageRecords());
				resultsPagination.setCurrentPageNumber(resultsPagination
						.getCurrentPageNumber() + 1);
			}
		}

		/**
		 * 
		 */
		private void toGetNewDataFromNet() {
			Log.d("doInBackgroundFunction", "getNewData1");
			results = new ArrayList<Result>();
			results = (ArrayList<Result>) resultBO.getNewData(currentPageSize);
			if (results != null) {
				resultsPagination.getRecords().addAll(results);
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
						"REPLACE INTO results(id ,title ,content ,image  ,editor ,publishTime) VALUES(?,?,?,?,?,?)",
						new Object[] { result.getId(), result.getTitle(),
								result.getContent(), result.getImage(),
								result.getEditor(), result.getPublishTime() });
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
								.getColumnIndex("publishTime")));
				results.add(result);
				c.moveToNext();
			}
			if (c.moveToLast())
				endTime = c.getString(c.getColumnIndex("publishTime"));
			Log.d("ResultsExhibitionFragment-doInBackgroundFunction",
					"firstTime = " + firstTime + "; endTime = " + endTime);
			c.close();
			db.close();
			resultsPagination.getRecords().addAll(results);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage_result, menu);
		item = menu.findItem(R.id.conent_new);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // 返回首页
			onBackPressed();
			return true;
		case R.id.conent_new:

			if (isAddState) {
				item.setIcon(R.drawable.navigation_accept);
				item.setTitle("确定");
				addNewResult();
				isAddState = !isAddState;
			} else {
				item.setIcon(R.drawable.content_new);
				item.setTitle("增加");
				addResult();
				isAddState = !isAddState;
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 增加成果到服务器
	 */
	private void addResult() {
		Log.d("ManageResult-addResult", "start");
		Result newResult = new Result();
		newResult.setTitle(addResultFragment.getTitle());
		newResult.setContent(addResultFragment.getContent());
		newResult.setImages(addResultFragment.getSelectedList());
		getSupportFragmentManager().popBackStack();

		Ding9App ding9App = (Ding9App) getApplication();

		newResult.setEditor(ding9App.getUsername());

		new AddDataTask(newResult);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 按下的如果是BACK，同时没有重复
			if (!isAddState) {
				item.setIcon(R.drawable.content_new);
				item.setTitle("增加");
				isAddState = !isAddState;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 添加新的成果
	 */
	private void addNewResult() {
		addResultFragment = new AddResultFragment();

		getSupportFragmentManager().beginTransaction()
				.add(R.id.container, addResultFragment).addToBackStack(null)
				.commit();
	}

	private class AddDataTask extends AsyncTask<Void, Void, String> {
		private boolean isConnect;
		private Result newResult;

		public AddDataTask(Result newResult) {
			this.newResult = newResult;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(Void... params) {
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);// 获取连接状况
			if (!isConnect) {// 无网络或未登陆则取消任务
				Log.d("doInBackground", "not network");
				cancel(true);
				return "not network";
			}
			return resultBO.addNewResult(newResult);
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
			Log.d("onPostExecute", result);
			if (result.trim().equals("success")) {
				ToastUtils.show(activity, "新增成功");
			} else if (result.trim().equals("failure")) {
				ToastUtils.show(activity, "新增失败");
			} else {
				ToastUtils.show(activity, "新增情况未知");
			}
		}

		/**
	     * 
	     */
		private void cancelledFunction() {
			if (!isConnect) {
				ToastUtils.show(activity, "网络连接出现问题~");
			}
		}

		@Override
		protected void onCancelled() {
			cancelledFunction();
			super.onCancelled();
		}

	}

}
