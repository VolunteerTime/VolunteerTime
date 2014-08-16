/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-23
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
 * 成果展示中的ListView适配器(作为
 * 一个几乎所有用到ListView的例子，显示ImageCache和ding9App的用法，其实ImageCache更适合作为一个静态对象
 * ，这里只是填充对象，可如有更好的优化方法就进行优化)
 * 
 * @author 蔡超敏
 * 
 */
public class ResultsExhibitionListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ImageCache IMAGE_CACHE;// 图片缓存
	private Ding9App ding9App;
	private Pagination<Result> resultsPagination;// 成果数据
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

	@Override
	public int getCount() {
		if (resultsPagination != null
				&& resultsPagination.getPageSize() <= resultsPagination
						.getRecords().size()) {
			Log.d("ResultsExhibitionListAdapter-getCount", "1="
					+ resultsPagination.getPageSize() + ":"
					+ resultsPagination.getRecords().size());
			return resultsPagination.getPageSize();
		} else {
			Log.d("ResultsExhibitionListAdapter-getCount", "2");
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		Log.d("ResultsExhibitionListAdapter-getView", position
				+ (resultsPagination.getCurrentPageNumber() - 1)
				* resultsPagination.getPageSize() + "");
		Log.d("ResultsExhibitionListAdapter-getView", resultsPagination
				.getRecords().size() + "");
		Result result = resultsPagination.getRecords().get(
				position + (resultsPagination.getCurrentPageNumber() - 1)
						* resultsPagination.getPageSize());
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
		holder.articleContent.setDesc(result.getContent(), BufferType.NORMAL);
		Log.d("1", "ok");

		holder.publishTime.setText(result.getPublishTime());

		Log.d("2", "ok");
		return view;

	}

	static class ViewHolder {
		CollapsibleTextView articleContent;
		TextView publishTime;
	}

}
