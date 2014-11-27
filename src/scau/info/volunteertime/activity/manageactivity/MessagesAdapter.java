/**
 * Copyright (c) 华南农业大学信息学院蔡超敏2014版权所有
 * 
 * 文件创建时间：2014-11-26
 */
package scau.info.volunteertime.activity.manageactivity;

import java.util.LinkedList;

import scau.info.volunteertime.R;
import scau.info.volunteertime.application.Ding9App;
import scau.info.volunteertime.util.AgoTimeUtil;
import scau.info.volunteertime.vo.Message;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.trinea.android.common.service.impl.ImageCache;

/**
 * @author 蔡超敏
 * 
 */
public class MessagesAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ImageCache IMAGE_CACHE;// ͼƬ����
	private Ding9App ding9App;
	private LinkedList<Message> linkedList;
	private Context mContext;

	/**
	 * @param activity
	 * @param sortedLinkList
	 */
	public MessagesAdapter(Activity activity, LinkedList<Message> sortedLinkList) {
		super();
		this.inflater = LayoutInflater.from(activity);
		this.linkedList = sortedLinkList;
		this.IMAGE_CACHE = ding9App.IMAGE_CACHE;
		this.mContext = activity;
	}

	public void setListData(LinkedList<Message> linkedList) {
		this.linkedList = linkedList;
	}

	@Override
	public int getCount() {
		if (linkedList != null) {
			return linkedList.size();
		} else {
			Log.d("ActivityAdapter-getCount", "2");
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

		Log.d("ActivityAdapter-getView", "position = " + position);
		Message message = linkedList.get(position);
		if (view == null) {
			view = inflater.inflate(R.layout.item_message, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.message_title);
			holder.sender = (TextView) view.findViewById(R.id.message_sender);
			holder.time = (TextView) view.findViewById(R.id.message_time);
			//holder.iconitemactivitybook=(ImageView)view.findViewById(R.id.iconitemactivitybook2);
			
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		String checkStr = message.getIs_send() == 2 ? "[已查看]" : "[未查看]";
		
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.manage_coupon_div);
				
		if(checkStr.equals("[未查看]")) 
			{
			//System.out.println("asdfasdfasjhdflkasjdflkasjdflaskdjf  "+holder.iconitemactivitybook);
		//	holder.iconitemactivitybook.setImageResource(R.drawable.iconbook2);
			layout.setBackgroundResource(R.drawable.background_login_div_bg2);//未查看标有颜色
			}
		else 
			layout.setBackgroundResource(R.drawable.background_login_div_bg);
		
		holder.title.setText(checkStr + message.getTitle());

		holder.sender.setText(message.getLaunch_user_id());

		holder.time.setText(AgoTimeUtil.getTimeAgoFromCurren(message
				.getLaunch_time()));

		return view;

	}

	static class ViewHolder {
		TextView title;
		TextView sender;
		TextView time;
		ImageView iconitemactivitybook;
	}

}
