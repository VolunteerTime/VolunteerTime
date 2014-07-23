/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-15
 */
package scau.info.volunteertime.activity.resultsexhibition;

import cn.trinea.android.common.view.DropDownListView;
import scau.info.volunteertime.R;
import scau.info.volunteertime.business.ResultBO;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.Result;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results_exhibition);

		Log.d("ResultsExhibition", "create");

		hasMore = true;// �и������ʾ��û������ʾ

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
			}
		});
		
		resultsListView.onBottom();

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

}
