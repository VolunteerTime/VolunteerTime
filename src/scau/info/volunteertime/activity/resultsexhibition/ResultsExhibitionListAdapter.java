/**
 * Copyright (c) ����ũҵ��ѧ��ϢѧԺ�̳���2014��Ȩ����
 * 
 * �ļ�����ʱ�䣺2014-7-23
 */
package scau.info.volunteertime.activity.resultsexhibition;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.util.CollapsibleTextView;
import scau.info.volunteertime.util.Pagination;
import scau.info.volunteertime.vo.Result;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import cn.trinea.android.common.service.impl.ImageCache;

/**
 * �ɹ�չʾ�е�ListView������(��Ϊ
 * һ�����������õ�ListView�����ӣ���ʾImageCache��ding9App���÷�����ʵImageCache���ʺ���Ϊһ����̬����
 * ������ֻ�������󣬿����и��õ��Ż������ͽ����Ż�)
 * 
 * @author �̳���
 * 
 */
public class ResultsExhibitionListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ImageCache IMAGE_CACHE;// ͼƬ����
	private Ding9App ding9App;
	private Pagination<Result> resultsPagination;// �ɹ�����
	private Context mContext;

	public ResultsExhibitionListAdapter(Context context,
			Pagination<Result> resultsPagination) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.resultsPagination = resultsPagination;
		this.ding9App = (Ding9App) context.getApplicationContext();
		this.IMAGE_CACHE = ding9App.IMAGE_CACHE;
		this.mContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		if (resultsPagination != null && resultsPagination.getRecords() != null) {
			return resultsPagination.getRecords().size();
		} else {
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		if (resultsPagination != null && resultsPagination.getRecords() != null) {
			return resultsPagination.getRecords().get(position);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.item_results_exhibition, null);
			holder = new ViewHolder();
			holder.articleContent = (CollapsibleTextView) view
					.findViewById(R.id.results_exhibition_content);
			holder.publishTime = (TextView) view
					.findViewById(R.id.results_exhibition_time);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.articleContent.setDesc(
				resultsPagination.getRecords().get(position).getContent(),
				BufferType.NORMAL);
		Log.d("1", "ok");

		holder.publishTime.setText(resultsPagination.getRecords().get(position)
				.getPublishTime());

		Log.d("2", "ok");
		return view;

	}

	static class ViewHolder {
		CollapsibleTextView articleContent;
		TextView publishTime;
	}

}
