/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-8-14
 */
package scau.info.volunteertime.activity.resultsexhibition;

import java.util.ArrayList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.ResultBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.Pagination;
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
 * @author �̳���
 * 
 */
public class ResultsExhibitionFragment extends Fragment {

	private Pagination<Result> resultsPagination;// װ�ص�ǰ����
	private Pagination<Result> nextResultsPagination;// ��Ϊװ����һҳ���ݵ��н�

	private int currentPageSize = 10;

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

		resultsExhibitionListAdapter = new ResultsExhibitionListAdapter(
				activity, resultsPagination);// �ɹ����ݴ���Adapter

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
				resultsListView.onBottomComplete();
			}
		});

		resultsListView.onBottom();

		resultsListView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				Log.d("ResultsExhibition-onCreate-setOnDropDownListener-onClick",
						"��������");
				new GetDataTask(true).execute();
				resultsListView.onDropDownComplete();
			}
		});
		resultsListView.onDropDownComplete();

		return view;
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private boolean isDropDown;

		private int firstId;
		private int endId;

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

			} else {
				resultsExhibitionListAdapter.notifyDataSetChanged();
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
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			if (isDropDown) {

			} else {
				ArrayList<Result> results;
				if (resultsPagination.getAmountOfRecorders() == 0) {
					SQLiteDatabase db = activity.openOrCreateDatabase(
							"volunteertimedatabase.db", Context.MODE_PRIVATE,
							null);
					Cursor c = db.rawQuery("SELECT * FROM results", null);
					results = new ArrayList<Result>();
					c.moveToFirst();
					// firstId = c.getInt(c.getColumnIndex("id"));
					while (!c.isAfterLast()) {
						Result result = new Result(c.getInt(c
								.getColumnIndex("id")), c.getString(c
								.getColumnIndex("title")), c.getString(c
								.getColumnIndex("content")), c.getString(c
								.getColumnIndex("image")), c.getString(c
								.getColumnIndex("editor")), c.getString(c
								.getColumnIndex("publishTime")));
						results.add(result);
						c.moveToNext();
					}
					// c.moveToLast();
					// endId = c.getInt(c.getColumnIndex("id"));
					Log.d("ResultsExhibitionFragment-doInBackgroundFunction",
							"firstId = " + firstId + "; endId = " + endId);
					c.close();
					db.close();
					resultsPagination.getRecords().addAll(results);
					resultsPagination.setCurrentPageNumber(1);
					resultsPagination.setPageSize(currentPageSize);

					if (resultsPagination.getAmountOfRecorders() == 0) {
						Log.d("doInBackgroundFunction", "getNewData1");
						results = new ArrayList<Result>();
						results = (ArrayList<Result>) resultBO
								.getNewData(currentPageSize);
						if (results != null)
							resultsPagination.getRecords().addAll(results);
						Log.d("doInBackgroundFunction", "getNewData2");
					} else if (resultsPagination.getAmountOfRecorders() < currentPageSize) {
						ArrayList<Integer> integers = resultBO.update(firstId,
								endId);

						for (Integer i : integers) {
							resultsPagination.getRecords()
									.remove(new Result(i));
						}

						results = new ArrayList<Result>();
						results = (ArrayList<Result>) resultBO
								.getDownData(endId);
						if (results != null)
							resultsPagination.getRecords().addAll(results);
					}
				} else {
					if (currentPageSize < resultsPagination
							.getAmountOfRecorders()) {
						if (currentPageSize * 2 <= resultsPagination
								.getAmountOfRecorders()) {
							currentPageSize *= 2;
							resultsPagination.setPageSize(currentPageSize);
						} else {
							currentPageSize = resultsPagination
									.getAmountOfRecorders();
							resultsPagination.setPageSize(currentPageSize);
						}
					} else {
						results = new ArrayList<Result>();
						results = (ArrayList<Result>) resultBO
								.getDownData(endId);
						if (results != null)
							resultsPagination.getRecords().addAll(results);
					}
				}
			}
		}
	}

}
