/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity.resultsexhibition;

import scau.info.volunteertime.R;
import scau.info.volunteertime.business.ResultBO;
import scau.info.volunteertime.util.NetworkStateUtil;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.Result;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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
 * �ɹ�չʾ��activity
 * 
 * @author �̳���
 * 
 */
public class ResultsExhibition extends ActionBarActivity {

	private Pagination<Result> resultsPagination;// װ�ص�ǰ����
	private Pagination<Result> nextResultsPagination;// ��Ϊװ����һҳ���ݵ��н�

	private DropDownListView resultsListView;

	private ResultsExhibitionListAdapter resultsExhibitionListAdapter;

	private ResultBO resultBO;

	private boolean hasMore;

	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_exhibition);

		Log.d("ResultsExhibition", "create");

		resultsListView = (DropDownListView) findViewById(R.id.results_exhibition_list);

		hasMore = true;// �и������ʾ��û������ʾ
		activity = this;

		resultBO = new ResultBO();// ȡ��resultBO
		resultsPagination = new Pagination<Result>();

		resultsExhibitionListAdapter = new ResultsExhibitionListAdapter(this,
				resultsPagination);// �ɹ����ݴ���Adapter

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
				resultsListView.onBottomComplete();
			}
		});

		resultsListView.onBottom();

		resultsListView.setOnDropDownListener(new OnDropDownListener() {

			@Override
			public void onDropDown() {
				Log.d("ResultsExhibition-onCreate-setOnDropDownListener-onClick",
						"��������");
				resultsListView.onDropDownComplete();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results_exhibition, menu);
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

	private class GetDataTask extends AsyncTask<Void, Void, Void> {

		private boolean isConnect;
		private boolean isDropDown;

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
				if (resultsPagination != null
						&& resultsPagination.getRecords() != null)
					Log.d("couponsMessagesAdapter", resultsPagination
							.getRecords().size() + "");
				if (nextResultsPagination == null) {
					resultsExhibitionListAdapter = new ResultsExhibitionListAdapter(
							activity, resultsPagination);
					resultsListView.setAdapter(resultsExhibitionListAdapter);
				}
				resultsExhibitionListAdapter.notifyDataSetChanged();
				Log.d("couponsMessagesAdapter", "2");
				// should call onBottomComplete function of DropDownListView at
				// end of on bottom complete.
				resultsListView.onBottomComplete();
				Log.d("couponsMessagesAdapter", "����");
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
			resultsListView.onBottomComplete();
		}

		/**
		 * 
		 */
		private void doInBackgroundFunction() {
			if (isDropDown) {

			} else {
				if (resultsPagination == null
						|| resultsPagination.getRecords() == null) {
					Log.d("resultsPagination", "1");
					 resultsPagination = resultBO.getDownData(1);
					Log.d("resultsPagination", "resultsPagination == nullΪ "
							+ (resultsPagination == null));
				} else {
					// nextResultsPagination = resultBO.getDownData();

					hasMore = nextResultsPagination.getRecords().size() > resultsPagination
							.getRecords().size();
					Log.d("doInBackground", "hasMore= " + hasMore);

					resultsPagination
							.setCurrentPageNumber(nextResultsPagination
									.getCurrentPageNumber());
					resultsPagination.getRecords().addAll(
							nextResultsPagination.getRecords());

				}
			}
		}

	}

}
