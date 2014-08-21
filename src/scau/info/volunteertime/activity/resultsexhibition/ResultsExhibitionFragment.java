/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-8-14
 */
package scau.info.volunteertime.activity.resultsexhibition;

import java.util.ArrayList;

import scau.info.volunteertime.R;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
public class ResultsExhibitionFragment extends Fragment {

	private SortedLinkList<Result> sortedLinkList;
	private Pagination<Result> resultsPagination;// 临时数据的分页类

	private int currentPageSize = 8;
	private int currentPageNumber = 1;

	private DropDownListView resultsListView;

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

		resultBO = new ResultBO();// ȡ��resultBO
		resultsPagination = new Pagination<Result>();
		resultsPagination.setCurrentPageNumber(currentPageNumber);
		resultsPagination.setPageSize(currentPageSize);
		sortedLinkList = new SortedLinkList<Result>();

		resultsExhibitionListAdapter = new ResultsExhibitionListAdapter(
				activity, sortedLinkList.getList());// �ɹ����ݴ���Adapter

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_results_exhibition, null);

		resultsListView = (DropDownListView) view
				.findViewById(R.id.results_exhibition_list);

		resultsListView.setAdapter(resultsExhibitionListAdapter);

		resultsListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Log.d("ResultsExhibition-onCreate-setOnItemLongClickListener-onItemLongClick",
								"��������");
						return false;
					}

				});
		resultsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ResultsExhibition-onCreate-setOnItemClickListener-onItemClick",
						"��������");
			}
		});

		resultsListView.setOnBottomListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("ResultsExhibition-onCreate-setOnBottomListener-onClick",
						"��������");
				new GetDataTask(false).execute();
			}
		});

		resultsListView.onBottom();

		resultsListView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				Log.d("ResultsExhibition-onCreate-setOnDropDownListener-onClick",
						"��������");
				new GetDataTask(true).execute();
			}
		});
		resultsListView.onDropDownComplete();

		return view;
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
			isConnect = NetworkStateUtil.isNetworkAvailable(activity);// ��ȡ����״��
			if (!isConnect) {// ��������޸���������ȡ������
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
				ToastUtils.show(activity, "�������ӳ�������");
			} else if (!hasMore) {
				resultsListView.setFooterNoMoreText("û�и��������Ϣ��Ŷ~");
				ToastUtils.show(activity, "û�и��������Ϣ��Ŷ~");
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
				if (!hasMore) {// ��������޸���������ȡ������
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

}
