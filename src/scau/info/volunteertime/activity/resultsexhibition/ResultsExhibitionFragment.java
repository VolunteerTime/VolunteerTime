/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-8-14
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

		return view;
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
