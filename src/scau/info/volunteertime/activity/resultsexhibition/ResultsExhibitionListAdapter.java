/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-7-23
 */
package scau.info.volunteertime.activity.resultsexhibition;

import java.sql.Date;
import java.util.LinkedList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.vo.Result;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.trinea.android.common.service.impl.ImageCache;

/**
 * �ɹ�չʾ�е�ListView������(��Ϊ
 * һ�����������õ�ListView�����ӣ���ʾImageCache��ding9App
 * ���÷�����ʵImageCache���ʺ���Ϊһ����̬����
 * ������ֻ�������󣬿����и��õ��Ż������ͽ����Ż�)
 * 
 * @author 蔡超敏
 * 
 */
public class ResultsExhibitionListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ImageCache IMAGE_CACHE;// ͼƬ����
	private Ding9App ding9App;
	private LinkedList<Result> linkedList;
	private Context mContext;

	public ResultsExhibitionListAdapter(Context context,
			LinkedList<Result> linkedList) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.linkedList = linkedList;
		this.ding9App = (Ding9App) context.getApplicationContext();
		this.IMAGE_CACHE = ding9App.IMAGE_CACHE;
		this.mContext = context;
	}

	public void setListData(LinkedList<Result> linkedList) {
		this.linkedList = linkedList;
	}

	@Override
	public int getCount() {
		if (linkedList != null) {
			return linkedList.size();
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

		Log.d("ResultsExhibitionListAdapter-getView", "position = " + position);
		Result result = linkedList.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.item_results_exhibition, null);
			holder = new ViewHolder();
			holder.title = (TextView) view
					.findViewById(R.id.results_exhibition_title);
			holder.articleContent = (TextView) view
					.findViewById(R.id.results_exhibition_content);
			holder.publishTime = (TextView) view
					.findViewById(R.id.results_exhibition_time);

			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.title.setText("[" + result.getTitle() + "]");

		holder.articleContent.setText(result.getContent());

		holder.publishTime.setText(new Date(result.getDate()).toLocaleString());

		return view;

	}

	static class ViewHolder {
		TextView title;
		TextView articleContent;
		TextView publishTime;
	}

}
